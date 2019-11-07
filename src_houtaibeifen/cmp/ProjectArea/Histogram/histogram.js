import React from 'react';
import Component from '../../../core/MyComponent';
import Style from './histogram.module.css';
import { Modal } from 'antd';
import {LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip} from 'recharts';
// import Static from './static';

class Histogram extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: '',
            data: [],
            iWid: 0,
        }
    }

    componentDidMount() {
        this.on('nav_histogram', () => {
          this.setState({
              visible: true, 
          })
          this.static()
        })
      }

    handleCancel = () => {
        this.setState({
          visible: false,
        })
      };

    handleOk = () => {
        this.setState({
          visible: false,
        })
    }

    static() {
      const { RefImg } = this.props;
      // console.log(RefImg);
      if(RefImg) {
          var img = new Image();
          img.crossorigin = 'Anonymous';
          img.src = RefImg;

          var canvas = document.createElement('canvas');
          // canvas.width = img.naturalWidth;
          // console.log(img.naturalWidth);
          // canvas.height = img.naturalHeight;
          var ctx = canvas.getContext('2d');
          img.onload = function() {
              canvas.width = img.width;
              canvas.height = img.height;
              ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
          // }
          // console.log(img.width);
          // let obj = this.count4grey(ctx, img);
              var greyscale=[];
              var xlabel=[];
              var ylabel=[];

              var k=0;
              var imgdata = ctx.getImageData(0,0,img.width,img.height).data;
              // console.log(imgdata[0]);
              // console.log(imgdata[0+1]);
              // console.log(imgdata[0+2]);
              // console.log(imgdata[0+3]);
              for(let i=0;i<imgdata.length;i+=4) {
              // greyscale[k] = 0.299*imgdata[i] + 0.587*imgdata.data[i+1] + 0.114*imgdata.data[i+2]; //同matlab中的rgb2gray函数
                  greyscale[k] = (imgdata[i] + imgdata[i+1] + imgdata[i+2])/3;
              // greyscale[k] = imgdata.data[i+3];
                  k+=1;
              }

              var max=greyscale[0];
              var min=greyscale[0];
              for(let i=0;i<k;i+=1) {
                  if(greyscale[i]>max) {
                      max=greyscale[i];
                  }
                  if(greyscale[i]<min) {
                      min=greyscale[i];
                  }
              }

              const delta=(max-min)/20; //将区域内的灰度分为20级
              for(let i=0;i<21;i+=1) {
                  xlabel[i] = min+delta*i;
              }

          // 统计各灰度等级下的像素数
              for(let i=0;i<20;i+=1) {
                  let counter=0;
                  for(let j=0;j<k;j+=1) {
                      if(greyscale[j]>=xlabel[i]&&greyscale[j]<=xlabel[i+1]) {
                          counter=counter+1;
                      }
                  }
                  ylabel[i]=counter;
              } 
 
              var data=[];
              for(let i=0;i<20;i+=1) {
                  data[i]={name: xlabel[1+i], amount: ylabel[i]};
              }
              // console.log(data);
              this.setState({
                data: data,
                iWid: img.width,
              })
          }.bind(this)
        }
        else {
          const data=[]
          this.setState({
            data: data,
          })
        }
    }

    render() {
        const { lg, RefImg } = this.props;
        const { data, iWid } = this.state;

        return (
            <div>
            <Modal
              title={lg.histogram}
              visible={this.state.visible}
              okText={lg.OK}
              cancelText={lg.cancel}
              onOk={this.handleOk}
              onCancel={this.handleCancel}
              width={iWid<500 ? '600px' : (iWid+100).toString()+'px'}
            >
              
              <div className={Style.refimg}>
                <img src={RefImg} crossorigin='Anonymous' style={{display: 'block', margin: 'auto'}}/>
                <div> {lg.statZone} </div>
                <LineChart
                 width={500} 
                 height={500} 
                 data={data}
                 margin={{top: 5, left: 5, right: 5, bottom: 5}}
                >
                    <CartesianGrid strokeDasharray="3 3"/>
                    <XAxis dataKey='name'/>
                    <YAxis />
                    <Tooltip />
                    <Line type='monotone' dataKey='amount'/>
                </LineChart>
                <div> {lg.histogram} </div>
              </div>
            
            </Modal>
            </div>
        );

    }
}

export default Histogram;