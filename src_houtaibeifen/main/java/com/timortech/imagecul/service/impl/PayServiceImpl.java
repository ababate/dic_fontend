package com.timortech.imagecul.service.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
//import com.lly835.bestpay.enums.BestPayTypeEnum;
//import com.lly835.bestpay.model.PayRequest;
//import com.lly835.bestpay.model.PayResponse;
//import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.timortech.imagecul.base.BaseService;
import com.timortech.imagecul.base.TimorException;
import com.timortech.imagecul.config.ImageConfig;
import com.timortech.imagecul.config.WechatPayProperties;
import com.timortech.imagecul.entity.Pay;
import com.timortech.imagecul.entity.User;
import com.timortech.imagecul.enums.ExceptionEnum;
import com.timortech.imagecul.repository.PayRepository;
import com.timortech.imagecul.repository.UserRepository;
import com.timortech.imagecul.service.PayService;
import com.timortech.imagecul.utils.DateUtils;
import com.timortech.imagecul.utils.ImageUtils;
import com.timortech.imagecul.utils.MD5Utils;
import com.timortech.imagecul.utils.MapUtils;
import com.timortech.imagecul.utils.MoneyUtil;
import com.timortech.imagecul.utils.UniqueKeyUtils;
import com.timortech.imagecul.utils.WechatUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:58
 * @desc：
 */
@Slf4j
@Service
public class PayServiceImpl extends BaseService implements PayService {

	private final PayRepository payRepository;

	private final WxPayService wxPayService;

	private final WechatPayProperties wechatPayProperties;

	private final ImageConfig imageConfig;

	private final UserRepository userRepository;

	@Autowired
	public PayServiceImpl(PayRepository payRepository, WxPayService wxPayService, WechatPayProperties wechatPayProperties, ImageConfig imageConfig, UserRepository userRepository) {
		this.payRepository = payRepository;
		this.wxPayService = wxPayService;
		this.wechatPayProperties = wechatPayProperties;
		this.imageConfig = imageConfig;
		this.userRepository = userRepository;
	}

	@Override
	public Pay recharge(double amount) {
		User user = getUser();
//		WxPayUnifiedOrderRequest request = getRequest(amount,"数组图像匹配系统余额充值",imageConfig.getBaseUrl()+"/pay/notify",UniqueKeyUtils.getUniqueKey());
		WxPayUnifiedOrderRequest request = getRequest(amount,"数组图像匹配系统余额充值","http://imagecul.s1.natapp.cc/imagecul"+"/pay/notify",UniqueKeyUtils.getUniqueKey());
		Pay pay = new Pay();
		try {
			WxPayUnifiedOrderResult result = wxPayService.unifiedOrder(request);
			byte[] codeURL = wxPayService.createScanPayQrcodeMode2(result.getCodeURL(),null,null);
			String fileName = DateUtils.getFormatDate()+".jpg";
			String path = imageConfig.getLocation()+
					File.separator + user.getUsername()+
					File.separator + "codeUrl";
			File temp = new File(path);
			if(!temp.exists()){
				temp.mkdirs();
			}
			String codeUrl = path + File.separator + fileName;
			ImageUtils.byte2File(codeURL,codeUrl);
			pay.setAmount(amount);
			pay.setSerialNum(request.getOutTradeNo());
			pay.setCodeUrl(user.getUsername()+"/codeUrl/"+fileName);
			log.info("【微信支付】统一下单结果：{}",result);
		} catch (WxPayException | IOException e) {
			e.printStackTrace();
		}
		pay.setUserId(user.getId());
		pay.setType("WECHAT");
		pay = payRepository.save(pay);
		Pay result = pay;
		result.setCodeUrl(imageConfig.getBaseUrl()+pay.getCodeUrl());
		return result;
	}

	@Override
	public void notify(String notifyData) {
		try {
			WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(notifyData);
			String outTradeNo = result.getOutTradeNo();
			Pay pay = payRepository.getBySerialNum(outTradeNo);
			pay.setStatus("已支付");
			pay.setPayTime(new Date());
			payRepository.save(pay);
			log.info("【异步通知结果】{}",result);
		} catch (WxPayException e) {
			throw new TimorException(ExceptionEnum.NOTIFY_FIAL);
		}
	}

	@Override
	public List<Pay> list() {
		User user = getUser();
		return payRepository.getByUserIdAndStatusOrderByCreateTimeDesc(user.getId(),"已支付");
	}

	@Override
	public void adminRecharge(String userName, double amount) {
		User user = userRepository.getByUsername(userName);
		user.setBalance(user.getBalance()+amount);
		userRepository.save(user);
	}

	@Override
	public String queryStatus(Integer id) {
		Pay pay = payRepository.getById(id);
		return pay.getStatus();
	}

	private WxPayUnifiedOrderRequest getRequest(double amount,String orderName,String notifyUrl,String productId){
		WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
		request.setNonceStr(MD5Utils.encode(UniqueKeyUtils.getUniqueKey()));
		request.setSign(WechatUtils.sign(MapUtils.buildMap(request),wechatPayProperties.getMchKey()));
		request.setOutTradeNo(UniqueKeyUtils.getUniqueKey());
		request.setTotalFee(MoneyUtil.Yuan2Fen(amount));
		request.setBody(orderName);
		request.setTradeType("NATIVE");
		request.setNotifyUrl(notifyUrl);
		request.setSpbillCreateIp("8.8.8.8");
		request.setProductId(productId);
		return request;
	}
}
