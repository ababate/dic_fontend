import React from 'react';
import Component from '../../../core/MyComponent';
import { Input, Modal,  Icon, Button, message } from 'antd';
import Style from './register.module.css';
class Register extends Component {
  constructor() {
    super();
    this.state = {
      username: '',
      nick: '',
      password: '',
      visible: false,
    }
  }
  componentWillUnmount() {
    this.off('nav_register');
  }
  componentDidMount() {
    this.on('nav_register', () => {
      this.setState({
        visible: true,
      })
    })
  }
  handleCancel = () => {
    this.setState({
      visible: false,
    })
  };
  handleOk() {
    const { username, password, nick } = this.state;
    const { lg } = this.props;
    if (username === '') {
      message.error(lg.userNameTip);
      return;
    }
    if (nick === '') {
      message.error(lg.name);
      return;
    }
    if (password === '') {
      message.error(lg.passwordTip);
      return;
    }
    // 一下代码是注册
    this.Utils.baseFetch({
      type:'POST',
      url:'/imagecul/user/register',
      data:{
        "username": username,
        "password": password,
        "name": nick
      },
      success: res => {
        if(res.code === 200){
          this.emit('addInfo', lg.registerAccount + nick);
          this.handleCancel();
        }
        else {
          message.error(res.msg);
        }
      },
    })


  }
  render() {
    // const { userName, password } = this.state;
    const { lg } = this.props;
    return (
      <Modal
        title={lg.register}
        visible={this.state.visible}
        okText={lg.register}
        onOk={this.handleOk.bind(this)}
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
            placeholder={lg.cname}
            prefix={<Icon type="edit" style={{ color: 'rgba(0,0,0,.25)' }} />}
            onChange={e => {this.setState({nick: e.target.value})}}
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
export default Register;