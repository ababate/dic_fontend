import React from 'react';
import Component from '../../core/MyComponent';
import Style from './footer.module.css';
import {
  Icon,
  Drawer
} from 'antd';
class Footer extends Component {
  constructor() {
    super();
    this.state = {
      drawerVisible: false,
      infos: [],
    }
  }
  onClose() {
    this.setState({
      drawerVisible: false,
    })
  }
  onShow() {
    this.setState({
      drawerVisible: true,
    })
  }
  addFooterInfo = (msg) => {
    const now = new Date();
    const timeStr = now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate() + ' ' +
      now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds();
    const infos = this.state.infos;
    infos.push(timeStr + '  ' + msg);
    this.setState({
      infos
    })
  }
  componentWillUnmount() {
    this.off('addInfo');
  }
  componentDidMount() {
    this.on('addInfo', this.addFooterInfo);
  }
  render() {
    const {
      infos
    } = this.state;
    let items = [];
    let curMsg = '';
    if (infos.length > 0) {
      curMsg = infos[infos.length - 1];
    }
    items = infos.map((v, index) => {
      return (
        <p key={index}>{v}</p>
      )
    })
    items.reverse();
    return (
      <div className={Style.footer}>
        <Drawer
          height = {400}
          // title="历史信息"
          placement={'bottom'}
          closable={false}
          onClose={this.onClose.bind(this)}
          visible={this.state.drawerVisible}
        >
        <div className={Style.panelbody}>
          {items}
        </div>

        </Drawer>
        <Icon onClick={this.onShow.bind(this)} className={Style.icon} type="info-circle" />
        {curMsg}
      </div>
    )
  }
}
export default Footer;