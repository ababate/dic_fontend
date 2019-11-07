import React from 'react';
import Component from '../../core/MyComponent';
import Style from './image.module.css';

class Image extends Component {
  constructor() {
    super();
  }

  changeImageState = () =>{
    // this.setState({
    //   isOpen: !this.state.isOpen,
    // })
    if(this.props.onChange) {
      this.props.onChange(this.props.data);
    }
  }

  render() {
    const { isChoose } = this.props;
    const { imageUrl,fileName } = this.props.data;
    return (
      <div className={Style.box}>
        <img style={{border:isChoose ? '2px solid #66C094':''}} className={Style.img} src = {imageUrl} onClick={()=>{this.changeImageState()}}/>
        <div className={Style.name}>{fileName}</div>
      </div>

    )
  }
}

export default Image;