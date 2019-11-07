import React from 'react';
import Component from '../../core/MyComponent';
import Style from './image.module.css';

class Image extends Component {
  constructor() {
    super();
  }

  imageOnClick() {
    this.props.setImage(this.props.data);
  }

  render() {
    const { thumbnailUrl,imageName } = this.props.data;
    // console.log(thumbnailUrl);
    return (
      <div className={Style.imgcmp}>
        <img onClick={ this.imageOnClick.bind(this) } className={Style.img} src = {thumbnailUrl}/>
        <div className={Style.text}>{imageName}</div>
      </div>

    )
  }
}

export default Image;