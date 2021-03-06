import React from 'react';
import Component from '../../../core/MyComponent';
import { Input, Modal, Icon, Button, message } from 'antd';
import Style from './login.module.css';
class Login extends Component {
  constructor() {
    super();
    this.state = {
      username: '',
      password: '',
      visible: false,
    }
  }
  componentWillUnmount() {
    this.off('nav_login');
  }
  componentDidMount() {
    this.on('nav_login', () => {
      this.setState({
        visible: true,
      })
    })

  }
  handleCancel = () => {
    this.setState({
      visible: false,
    })
  }
  handleOk = () => {
    const { username, password } = this.state;
    const { lg } = this.props;
    if (username === '') {
      message.error(lg.userNameTip);
      return;
    }
    if (password === '') {
      message.error(lg.passwordTip);
      return;
    }

    this.Utils.baseFetch({
      type:'POST',
      url:'/imagecul/user/login',
      data:{
        "username": username,
        "password": password
      },
      success: res => {
        if(res.code === 200){
          this.Utils.setCookie("token",res.data);
          this.handleCancel();
          this.emit('login', username);
          this.emit('addInfo',lg.loginAccount+username);
        }
        else {
          message.error(res.msg);
        }
      },
    })
  }
  render() {
    const { userName, password } = this.state;
    const { lg } = this.props;
    return (
      <Modal
        title={lg.login}
        visible={this.state.visible}
        okText={lg.login}
        onOk={this.handleOk}
        onCancel={this.handleCancel}
        cancelText={lg.cancel}
      >

        <div className={Style.form}>
          <div className={Style.title}>{lg.welcomeUse}</div>
          <Input
            className={Style.input}
            placeholder={lg.userName}
            prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
            onChange={e => {this.setState({username: e.target.value})}}
          />
          <Input
            className={Style.input}
            placeholder={lg.password}
            type="password"
            prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
            onChange={e => {this.setState({password: e.target.value})}}
          />
        </div>
      </Modal>
    )
  }
}
export default Login;