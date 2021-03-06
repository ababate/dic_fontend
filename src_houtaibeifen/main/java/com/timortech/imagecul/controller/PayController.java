package com.timortech.imagecul.controller;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.timortech.imagecul.base.MessageResponse;
import com.timortech.imagecul.base.ResultResponse;
import com.timortech.imagecul.entity.Pay;
import com.timortech.imagecul.service.PayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/pay")
@Api(tags = "支付",description = "/pay/*")
public class PayController {

	private final PayService payService;

	@Autowired
	public PayController(PayService payService) {
		this.payService = payService;
	}

	@GetMapping(value = "/recharge")
	@ApiOperation(value = "余额充值")
	public ResultResponse<Pay> recharge(@RequestParam(value = "amount") double amount,
	                                    @RequestParam(value = "type") String type){

		Pay pay = payService.recharge(amount);

		return new ResultResponse<>(pay);
	}

	@GetMapping(value = "/query/status")
	public ResultResponse<String> queryStatus(@RequestParam(value = "id") Integer id){
		String status = payService.queryStatus(id);
		return new ResultResponse<>(status);
	}

	@GetMapping(value = "/recharge/admin")
	public MessageResponse adminRecharge(@RequestParam(value = "username") String userName,
	                                     @RequestParam(value = "amount") double amount){
		payService.adminRecharge(userName,amount);
		return MessageResponse.success();
	}

	/**
	 * 微信支付异步通知响应
	 * @param notifyData
	 */
	@PostMapping(value = "/notify")
	public ModelAndView notify(@RequestBody String notifyData) throws WxPayException {
		payService.notify(notifyData);
		//给微信返回处理结果，结束异步通知
		return new ModelAndView("pay/success");
	}

	@GetMapping(value = "/list")
	@ApiOperation(value = "获取充值记录")
	public ResultResponse<List<Pay>> list(){
		List<Pay> pays = payService.list();
		return new ResultResponse<>(pays);
	}

}
