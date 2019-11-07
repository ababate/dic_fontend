import React from 'react';
import ReactDOM from 'react-dom';
import Component from '../../../core/MyComponent';
import Style from './ChooseDomain.module.css';
import { Button, Modal, Radio } from 'antd';
import ReactCrop from 'react-image-crop';
import "react-image-crop/dist/ReactCrop.css";

const RadioGroup = Radio.Group;

class ChooseDomain extends Component {
  constructor(props) {
    super(props);
    this.state = {
      visible: false,
      crop: {
        unit: "px"
      },
      modWid: "",
      imgWid: 0,
      imgHet: 0,
      croppedImageUrl: "",
      RefImg: "",
    }
  }

  componentDidMount() {
    this.on('nav_drawRect', () => {
      this.setState({
        visible: true,
        RefImg: this.props.fileA.originalUrl,
      })
    })
  }

  handleCancel = () => {
    this.setState({
      visible: false,
    })
  };

  handleOk = () => {
    const { crop, imgWid, imgHet, croppedImageUrl } = this.state;
    this.setState({
      visible: false,
    })

    if (crop.x>imgWid) {
      this.Cache.domainX = crop.x.toString()
    }
    else {
      this.Cache.domainX = (crop.x+1).toString()
    }

    if (crop.y>imgHet) {
      this.Cache.domainY = crop.y.toString()
    }
    else {
      this.Cache.domainY = (crop.y+1).toString()
    }

    if (crop.width-1<0) {
      this.Cache.domainW = '0'
    }
    else {
      this.Cache.domainW = (crop.width-1).toString()
    }

    if (crop.height-1<0) {
      this.Cache.domainH = '0'
    }
    else {
      this.Cache.domainH = (crop.height-1).toString()
    }

    this.Cache.croppedImg = croppedImageUrl;
    this.emit('setParams',this.Cache);
    // this.emit('nav_histogram',this.state.croppedImageUrl);
  };
  
  onImageLoaded = image => {
    this.imageRef = image;
    this.setState ({
      modWid: (image.naturalWidth + 100).toString() + "px",
      imgWid: image.naturalWidth,
      imgHet: image.naturalHeight,
    	});
  };
  
  onCropChange = (crop) => {
    this.setState({ crop });
  };

  onCropComplete = crop => {
    this.makeClientCrop(crop);
  };

  async makeClientCrop(crop) {
    if (this.imageRef && crop.width && crop.height) {
      const croppedImageUrl = await this.getCroppedImg(
        this.imageRef,
        crop,
        "newFile.jpeg"
      );
      this.setState({ croppedImageUrl });
    }
  }

  getCroppedImg(image, crop, fileName) {
    const canvas = document.createElement("canvas");
    // const scaleX = image.naturalWidth / image.width;
    // const scaleY = image.naturalHeight / image.height;
    canvas.width = crop.width;
    canvas.height = crop.height;
    const ctx = canvas.getContext("2d");

    ctx.drawImage(
      image,
      // crop.x * scaleX,
      // crop.y * scaleY,
      // crop.width * scaleX,
      // crop.height * scaleY,
      crop.x,
      crop.y,
      crop.width,
      crop.height,
      0,
      0,
      crop.width,
      crop.height
    );

    var imgdata = ctx.getImageData(0,0,crop.width,crop.height);
    var data = imgdata.data;
    for(let i=0;i<data.length;i+=4) {
      var avg = 0.299*data[i] + 0.587*data[i+1] + 0.114*data[i+2]; //同matlab中的rgb2gray函数
      data[i] = avg;
      data[i+1] = avg;
      data[i+2] = avg;
    }
    ctx.putImageData(imgdata,0,0);

    const base64Image = canvas.toDataURL('image/jpeg');
    return base64Image;
  }

  onChange = (e) => {
    this.setState({
      RefImg: e.target.value,
    });
  };

  render() {
    const { lg, fileA, fileB } = this.props;
    const { crop, modWid, imgWid, imgHet, RefImg } = this.state;

    // var imgA = new Image();
    // var imgB = new Image();

    // imgA.crossOrigin = '*';
    // imgB.crossOrigin = '*';

    // imgA.src = fileA.originalUrl;
    // imgB.src = fileB.originalUrl;

    // var imageA = this.getBase64Image(imgA);
    // var imageB = this.getBase64Image(imgB);

    return (
      <div>
        <Modal
          title={lg.chooseSpecialArea}
          visible={this.state.visible}
          okText={lg.OK}
          onOk={this.handleOk}
          onCancel={this.handleCancel}
          cancelText={lg.cancel}
          width={modWid}
        >
          
          <div className={Style.image}>
            <ReactCrop
              src={RefImg}
              crop={crop}
              onImageLoaded={this.onImageLoaded}
              onComplete={this.onCropComplete}
              onChange={this.onCropChange}
              crossorigin = 'Anonymous'
              // className={Style.crop}
            />

          <RadioGroup onChange={this.onChange} value={this.state.RefImg}>
            <Radio value={fileA.originalUrl}>Reference Image</Radio>
            <Radio value={fileB.originalUrl}>Deformed Image</Radio>
          </RadioGroup>

            <div className={Style.input}>
              <div>{lg.chooseAreaStartPoint}：X= {crop.x>imgWid ? crop.x : crop.x+1} px, Y= {crop.y>imgHet ? crop.y : crop.y+1} px.</div>
              <div>{lg.chooseAreaSize}：W= {(crop.width)-1<0 ? 0 : crop.width-1} px, H= {(crop.height)-1<0 ? 0 : crop.height-1} px.</div>
            </div>
          </div>

        </Modal>
      </div>
    );
  }
}

export default ChooseDomain;