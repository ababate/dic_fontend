import React from 'react';
import Component from '../../core/MyComponent';
import Folder from './folder';
import Style from './showArea.module.css';
import { Select, Input, Button, Row, Col } from 'antd';
// import Plot from 'react-plotly.js';

// import Cloudmap from './cloudmap';
// const InputGroup = Input.Group;
const { Option } = Select;

class ShowArea extends Component {
  constructor(props) {
    super(props);
    this.state = {
      folderData: [],
      visible: 'none',
      matData: [],
      showData: [],
      max: 'none',
      min: 'none',
      imgSrc: '',
      bgSrc: '',
      curProjectData: '',
    }
  }
  
  componentWillUnmount() {
    this.off('nav_checkRes');
  }
  
  componentDidMount() {
    this.on('nav_checkRes', (obj) => {
      if (obj.pageName !== 'show') {
        this.emit('changePage', 'show');
      }
      
    //获取结果
    this.Utils.baseFetch({
      type: 'GET',
      url:'/imagecul/task/result',
      success: res => {
        if(res.code === 200){
          res.data = res.data.map( v => {
            v.isOpen = false;
            return v
          });
          this.setState({
            folderData: res.data
          })
        }
      }
    })

    this.Utils.baseFetch({
      type: 'GET',
      url: '/imagecul/project/get/user',
      success: res => {
        if(res.code == 200){
          var curProject = {};
          res.data.forEach(function (v,index) {
            if(v.status == "OPEN")
              curProject = v;
          });
        }
        this.setState({
          curProjectData: curProject
        })
      }
    });
    });
  }
  
  folderOnChange(data) {
    data = this.Utils.deepCopy(data);
    let { folderData } = this.state;
    folderData = folderData.map( v => {
      if (!data.isOpen) {
        v.isOpen = false;
      }
      if (data.id === v.id) {
        v.isOpen = !v.isOpen;
        if (v.isOpen&&v.status === 'COMPLETED') {
          this.setState({
            visible: 'block',
            matData: v,
            bgSrc: v.imageaUrl,
            showData: []
          })
        }
        else {
          this.setState({
            visible: 'none',
            matData: [],
            bgSrc: '',
            showData:[]
          })
        }
      }
      return v;
    });
    
    this.setState({
      folderData
    })
  }

  Mat2Img(matrix, max, min, sx, sy, bgSrc) {
    //提示
    if (matrix.length==0) {
      alert('Please choose a variable');
      return;
    }

    console.log(max,min,max-min);
    // const { bgSrc } = this.state;
    var bgi = new Image();
    bgi.src = bgSrc;
    const H = bgi.naturalHeight;
    const W = bgi.naturalWidth;

    //确定矩阵的大小
    const h = matrix.length;
    const w = matrix[0].length;

    if(max=='none') {
        max = matrix[0][0];
        for(let i=1;i<h;i+=1) {
            for(let j=1;j<w;j+=1) {
                if(matrix[i][j]>max) {
                    max = matrix[i][j];
                }
            }
        }
    }
    // console.log(max);
    if(min=='none') {
        min = matrix[0][0];
        for(let i=1;i<h;i+=1) {
            for(let j=1;j<w;j+=1) {
                if(matrix[i][j]<min) {
                    min = matrix[i][j];
                }
            }
        }
    }
    // console.log(min);
    //colorbar的step
    const delta = (max - min)/16;
    //初始化RGB空矩阵
    var A = new Array();
    for(let i=0;i<h;i+=1) {
        A[i] = new Array();
        for(let j=0;j<w;j+=1) {
            A[i][j] = new Array(3);
            
            //Colorization
            if(matrix[i][j]<min) {
                A[i][j][0] = 137;
                A[i][j][1] = 0;
                A[i][j][2] = 255;
            }
            else if(min<=matrix[i][j]&&matrix[i][j]<=min+delta) {
                A[i][j][0] = 0;
                A[i][j][1] = 92;
                A[i][j][2] = 255;
            }
            else if(min+delta<matrix[i][j]&&matrix[i][j]<=min+2*delta) {
                A[i][j][0] = 0;
                A[i][j][1] = 165;
                A[i][j][2] = 255;
            }
            else if(min+2*delta<matrix[i][j]&&matrix[i][j]<=min+3*delta) {
                A[i][j][0] = 0;
                A[i][j][1] = 209;
                A[i][j][2] = 255;
            }
            else if(min+3*delta<matrix[i][j]&&matrix[i][j]<=min+4*delta) {
                A[i][j][0] = 0;
                A[i][j][1] = 248;
                A[i][j][2] = 255;
            }
            else if(min+4*delta<matrix[i][j]&&matrix[i][j]<=min+5*delta) {
                A[i][j][0] = 0;
                A[i][j][1] = 255;
                A[i][j][2] = 233;
            }
            else if(min+5*delta<matrix[i][j]&&matrix[i][j]<=min+6*delta) {
                A[i][j][0] = 0;
                A[i][j][1] = 255;
                A[i][j][2] = 192;
            }
            else if(min+6*delta<matrix[i][j]&&matrix[i][j]<=min+7*delta) {
                A[i][j][0] = 0;
                A[i][j][1] = 255;
                A[i][j][2] = 141;
            }
            else if(min+7*delta<matrix[i][j]&&matrix[i][j]<=min+8*delta) {
                A[i][j][0] = 73;
                A[i][j][1] = 255;
                A[i][j][2] = 0;
            }
            else if(min+8*delta<matrix[i][j]&&matrix[i][j]<=min+9*delta) {
                A[i][j][0] = 164;
                A[i][j][1] = 255;
                A[i][j][2] = 0;
            }
            else if(min+9*delta<matrix[i][j]&&matrix[i][j]<=min+10*delta) {
                A[i][j][0] = 209;
                A[i][j][1] = 255;
                A[i][j][2] = 0;
            }
            else if(min+10*delta<matrix[i][j]&&matrix[i][j]<=min+11*delta) {
                A[i][j][0] = 245;
                A[i][j][1] = 255;
                A[i][j][2] = 0;
            }
            else if(min+11*delta<matrix[i][j]&&matrix[i][j]<=min+12*delta) {
                A[i][j][0] = 255;
                A[i][j][1] = 237;
                A[i][j][2] = 0;
            }
            else if(min+12*delta<matrix[i][j]&&matrix[i][j]<=min+13*delta) {
                A[i][j][0] = 255;
                A[i][j][1] = 198;
                A[i][j][2] = 0;
            }
            else if(min+13*delta<matrix[i][j]&&matrix[i][j]<=min+14*delta) {
                A[i][j][0] = 255;
                A[i][j][1] = 149;
                A[i][j][2] = 0;
            }
            else {
                A[i][j][0] = 255;
                A[i][j][1] = 0;
                A[i][j][2] = 0;
            }
        } 
    }
    
    // 创建canvas并进行绘图
    const canvas = document.createElement("canvas");
    canvas.width = W;
    canvas.height = H+H/6;
    const ctx = canvas.getContext('2d');

    bgi.onload = function() {
      ctx.drawImage(bgi,0,0,W,H);
      var imgdata1 = ctx.getImageData(sx-1,sy-1,w,h);
      var data1 = imgdata1.data;
      // var k = 0;
      for(let x=0;x<h;x+=1) {
          // var l = 0;
          for(let y=0;y<w;y+=1) {
              let I = 4*(x*w+y);
              data1[I] = 0.1*data1[I]+0.9*A[x][y][0];
              data1[I+1] = 0.1*data1[I+1]+0.9*A[x][y][1];
              data1[I+2] = 0.1*data1[I+2]+0.9*A[x][y][2];
              data1[I+3] = 255;
              // l=l+1;
          }
          // k=k+1;
      } 
      ctx.putImageData(imgdata1,sx-1,sy-1);

      //创建colorbar
      var cx = Math.round(W*0.05);
      var cy = Math.round(H+H/60);
      var cw = Math.round(W*0.9);
      var ch = Math.round(H/20);
      var d = Math.round(W*0.9/16);
      var imgdata2 = ctx.createImageData(cw,ch);
      var data2 = imgdata2.data;
      for(let x=0;x<ch;x+=1) {
        for(let y=0;y<d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 137;
          data2[I+1] = 0;
          data2[I+2] = 255;
          data2[I+3] = 255;
        }
        for(let y=d;y<2*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 0;
          data2[I+1] = 92;
          data2[I+2] = 255;
          data2[I+3] = 255;
        }
        for(let y=2*d;y<3*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 0;
          data2[I+1] = 165;
          data2[I+2] = 255;
          data2[I+3] = 255;
        }
        for(let y=3*d;y<4*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 0;
          data2[I+1] = 209;
          data2[I+2] = 255;
          data2[I+3] = 255;
        }
        for(let y=4*d;y<5*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 0;
          data2[I+1] = 248;
          data2[I+2] = 255;
          data2[I+3] = 255;
        }
        for(let y=5*d;y<6*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 0;
          data2[I+1] = 255;
          data2[I+2] = 233;
          data2[I+3] = 255;
        }
        for(let y=6*d;y<7*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 0;
          data2[I+1] = 255;
          data2[I+2] = 192;
          data2[I+3] = 255;
        }
        for(let y=7*d;y<8*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 0;
          data2[I+1] = 255;
          data2[I+2] = 141;
          data2[I+3] = 255;
        }
        for(let y=8*d;y<9*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 73;
          data2[I+1] = 255;
          data2[I+2] = 0;
          data2[I+3] = 255;
        }
        for(let y=9*d;y<10*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 164;
          data2[I+1] = 255;
          data2[I+2] = 0;
          data2[I+3] = 255;
        }
        for(let y=10*d;y<11*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 209;
          data2[I+1] = 255;
          data2[I+2] = 0;
          data2[I+3] = 255;
        }
        for(let y=11*d;y<12*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 245;
          data2[I+1] = 255;
          data2[I+2] = 0;
          data2[I+3] = 255;
        }
        for(let y=12*d;y<13*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 255;
          data2[I+1] = 237;
          data2[I+2] = 0;
          data2[I+3] = 255;
        }
        for(let y=13*d;y<14*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 255;
          data2[I+1] = 198;
          data2[I+2] = 0;
          data2[I+3] = 255;
        }
        for(let y=14*d;y<15*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 255;
          data2[I+1] = 149;
          data2[I+2] = 0;
          data2[I+3] = 255;
        }
        for(let y=15*d;y<16*d;y+=1) {
          let I = 4*(x*cw+y);
          data2[I] = 255;
          data2[I+1] = 0;
          data2[I+2] = 0;
          data2[I+3] = 255;
        }
      }
      ctx.putImageData(imgdata2,cx,cy);

      ctx.font=Math.round(H/25).toString()+'Arial';
      max = max.toFixed(4);
      min = min.toFixed(4);
      ctx.fillText(min.toString(), cx+d, cy+ch+Math.round(H/30));
      ctx.fillText(max.toString(), cx+15*d, cy+ch+Math.round(H/30));

      const base64Image = canvas.toDataURL('image/png');
      this.setState({
          imgSrc: base64Image
      });
    }.bind(this);
  }

  onhandleChange(value) {
    const { matData } = this.state;
    this.setState({
      showData: matData[value]
    })
  }

  onHandleClick() {
    const { showData, max, min, curProjectData, bgSrc } = this.state;
    var a = curProjectData.domainX;
    var b = curProjectData.domainY;
    this.Mat2Img(showData, max, min, a, b, bgSrc);
  }

  render() {
    const { lg } = this.props;
    const { folderData, visible, imgSrc } = this.state;
    let items = folderData.map( (v, index) => {
      return (<Folder key={index} className={Style.folder} data={v} isOpen={v.isOpen} onChange={this.folderOnChange.bind(this)} />)
    });
    
    return (
      <div style={{height: "calc(100% - 2px)"}}>

        {/*左边图片文件夹*/}
        <div className={Style.left}>
          <div className={Style.top}>{lg.resultList}</div>
          {items}
        </div>

        {/*右侧结果展示*/}
        <div className={Style.right}>

          <div className={Style.main}>

            <div style={{display: visible}}>
            <div style={{width: '1000px', margin: '0 auto', paddingTop: '30px', paddingBottom: '30px'}}>
              <div style={{width: '120px', color: '#F9F9F9'}}>展示变量</div>
              <Select defaultValue={'Choose a Variable'} 
                      style={{width: '180px'}} 
                      onChange={this.onhandleChange.bind(this)}
              >
                <Option value={'resultU'}>U</Option>
                <Option value={'resultV'}>V</Option>
                <Option value={'resultExx'}>Exx</Option>
                <Option value={'resultExy'}>Exy</Option>
                <Option value={'resultEyy'}>Eyy</Option>
                <Option value={'resultEf'}>E1</Option>
                <Option value={'resultEs'}>E2</Option>
              </Select>
              <div style={{width: '200px', paddingTop: '10px', color: '#F9F9F9'}}>自定义最大/小数值</div>
              <Input placeholder='Maximum Value' 
                     onChange={e => {this.setState({max: e.target.value == '' ? 'none' : e.target.value})}} 
                     style={{width: '150px', textAlign: 'center'}}
              />
              <Input placeholder='Minimum Value' 
                     onChange={e => {this.setState({min: e.target.value == '' ? 'none' : e.target.value})}} 
                     style={{width: '150px', textAlign: 'center'}}
              />
              <Button onClick={()=>{this.onHandleClick()}} style={{width: '80px', textAlign: 'center'}}>展示</Button>
            </div>

            <div style={{width: '1000px', paddingTop: '30px', paddingBottom: '30px', margin: '0 auto'}}>
              <img src={imgSrc} width={'1000px'}/>
            </div>
            </div>

          </div>
        </div>

      </div>

    )
  }
}

export default ShowArea;