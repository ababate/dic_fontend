import React from 'react';
import Component from '../core/MyComponent';
import Style from './app.module.css';
import { Icon, Drawer,message } from 'antd';
import Pages from './pages';
import Footer from './apphelper/footer';
import Step from './apphelper/step';
let ZH = require('../json/lg/zh.json')
let ENG = require('../json/lg/eng.json')

class AppCmp extends Component {
  constructor() {
    super();
    this.state = {
      pageName: 'project',
      lg: {},
      isLogin: false,
      username: '',
    }
  }
  emitMsg = (event) => {
    const now = new Date();
    const timeStr = now.getFullYear() + '-' + (now.getMonth()+1) + '-' + now.getDate() + ' '
      + now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds();
    const { pageName } = this.state;
    this.emit(event,{ time:timeStr, pageName });
    // this.setState({
    //   pageName
    // })
  }
  // 返回欢迎页
  close = () => {
    this.Utils.setCookie('token', '');
    this.props.history.push("/");

  }
  // 登陆就显示登陆的nav
  login = (username) => {
    this.setState({
      isLogin: true,
      username,
      pageName: 'project',
    })
  };
  //  changePage
  changePage = pageName => {
    this.setState({
      pageName
    })
  };

  componentWillUnmount() {
    this.off('login');
    this.off('close');
    this.off('changePage');
  }

  componentDidMount() {
    this.on('login', this.login);
    this.on('close', this.close);
    this.on('changePage', this.changePage);
    // this.on('nav_login', (info) => {
    // });
    this.showEvent();
    // 设置语言包
    const lg = this.Utils.getCookie('lg');
    if (lg === 'eng') {
      this.setState({lg: ENG});
    } else {
      this.setState({lg: ZH});
    }
    // 是否登陆
    const token = this.Utils.getCookie('token');
    if(token){
      // 登陆成功后，获取用户信息
      this.Utils.baseFetch({
        type:'GET',
        url:'/imagecul/user/get',
        success: res => {
          if(res.code === 200){
            this.emit('addInfo', this.state.lg.loginAccount+res.data.username);
            this.emit('login',res.data.username);
          }
          else {
            message.error(res.msg);
          }
        },
      })
    }
  }
  render() {
    const { lg, isLogin, username } = this.state;
    const { pageName } = this.state;
    return (
      <div className={Style.body} align="center">
    <div style={{
        height:790,
        width:1640
    }} align =" center">
      <div className={Style.wrap}>
        <header className={Style.header}>
          <img src='./images/logo1.ico'/>
          <span>{lg.AppTitle}</span>
        </header>
        
        <div className={Style.nav}>
          <div className={Style.downlist}>
            <div className={Style.title}>{lg.file}</div>
            <div className={Style.body}>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_new')}}>{lg.newProject}</button>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_open')}}>{lg.open}</button>
              {/*<button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_save')}}>{lg.save}</button>*/}
              {/*<div className={Style.listBtn} onClick={()=>{this.emitMsg('nav_saveAll')}}>{lg.saveAll}</div>*/}
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_export')}}>{lg.exportData}</button>
              <button className={Style.listBtn} onClick={this.close}>{lg.exit}</button>
            </div>
          </div>
          {/* <div className={Style.downlist}>
            <div className={Style.title}>{lg.area}</div>
            <div className={Style.body}>
              <div className={Style.listBtn} onClick={()=>{this.setState({pageName:'action'})}}>{lg.imageInteraction}</div>
              <div className={Style.listBtn} onClick={()=>{this.setState({pageName:'show'})}}>{lg.resultShow}</div>
            </div>
          </div> */}
          <div className={Style.downlist}>
            <div className={Style.title}>{lg.image}</div>
            <div className={Style.body}>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_histogram')}}>{lg.histogram}</button>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_imageProcess')}}>{lg.imageProcess}</button>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_referenceImage')}}>{lg.referenceImage}</button>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_deformedImage')}}>{lg.deformedImage}</button>
            </div>
          </div>

          <div className={Style.downlist}>
            <div className={Style.title}>{lg.edit}</div>
            <div className={Style.body}>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_drawRect')}}>{lg.drawRect}</button>
              {/*<button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_cutoffArea')}}>{lg.cutoffArea}</button>*/}
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_calibration')}}>{lg.calibration}</button>
            </div>
          </div>

          <div className={Style.downlist}>
            <div className={Style.title}>{lg.calculate}</div>
            <div className={Style.body}>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_chooseAlgorithm')}}>{lg.chooseAlgorithm}</button>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_setParams')}}>{lg.setParam}</button>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_setVariable')}}>{lg.setVariable}</button>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_run')}}>{lg.startRun}</button>
            </div>
          </div>

          <div className={Style.downlist}>
            <div className={Style.title}>{lg.postProcess}</div>
            <div className={Style.body}>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_checkRes')}}>{lg.checkRes}</button>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_addFunc')}}>{lg.addFunc}</button>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_visualization')}}>{lg.visualization}</button>
              <button className={Style.listBtn} onClick={()=>{this.emitMsg('nav_checkout')}}>{lg.checkout}</button>
            </div>
          </div>

          <div className={Style.downlist}>
            <div className={Style.title} onClick={()=>{this.emitMsg('nav_help')}}>{lg.help}</div>
          </div>
          <div className={Style.downlist}>
            <div className={Style.title} onClick={()=>{this.emitMsg('nav_about')}}>{lg.about}</div>
          </div>
          <div className={[Style.downlist,isLogin?Style.hide:''].join(' ')}>
            <div className={Style.title} onClick={()=>{this.emitMsg('nav_login')}}>{lg.login}</div>
          </div>
          <div className={[Style.downlist,isLogin?'':Style.hide].join(' ')}>
            <div className={Style.title}>{lg.mine}</div>
            <div className={Style.body}>
              <div className={Style.listText}>{username}</div>
              <div className={Style.listBtn} onClick={()=>{this.emitMsg('nav_recharge')}}>{lg.recharge}</div>
              <div className={Style.listBtn} onClick={()=>{this.emitMsg('nav_account')}}>{lg.account}</div>
              <div className={Style.listBtn} onClick={this.close}>{lg.exit}</div>
            </div>
          </div>
          <div className={[Style.downlist,isLogin?Style.hide:''].join(' ')}>
            <div className={Style.title} onClick={()=>{this.emitMsg('nav_register')}}>{lg.register}</div>
          </div>
        </div>

        <div className={Style.content}>
          <Pages pageName={pageName} lg={lg}/>
        </div>
        
        <Footer/>
        <Step/>

      </div>
     </div>
    </div>
    )
  }
}
export default AppCmp;