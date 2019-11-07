import React from 'react';
import { Popover } from 'antd';
import Component from '../../core/MyComponent';
import Style from './folder.module.css';

class Folder extends Component {
  constructor(props) {
    super(props);
    this.state = {
    }
  }

  changeOpenState = () =>{
    if(this.props.onChange) {
      this.props.onChange(this.props.data);
    }
  }

  render() {
    const { id ,status,price} = this.props.data;
    const { isOpen } = this.props;
    const { className } = this.props;
    const content = (
      <div style={{color:status=="COMPLETED" ?'green':'red'}}>
        <p>{id}</p>
        <p>-{price}元</p>
        <p>{status=="COMPLETED" ?'成功':'失败'}</p>
      </div>
    );
    return (
      <div className={className}>
        {/* 右边卡片提示 */}
        <Popover content={content} title="计算结果详情" placement="rightTop">
          <img className={Style.img} src={isOpen ? './images/result-open.png' : './images/result.png'} onClick={()=>{this.changeOpenState()}}/>
        </Popover>
        <div className={Style.text}>{id}</div> 
      </div>

    )
  }
}

export default Folder;