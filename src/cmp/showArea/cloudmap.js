import React from 'react';
import Component from '../../core/MyComponent';
import { Button } from 'antd';
// import Plot from 'react-plotly.js';

// import colorbar from 'colourbar';
// import ndarray from 'ndarray';
// import Imshow from 'ndarray-imshow';

class Cloudmap extends Component {
    constructor(props) {
        super(props);
        this.state = {
            // max: 'none',
            // min: 'none',
            imgSrc: '',
        }
    }

    Mat2Img(matrix, max, min) {

        //确定矩阵的大小
        const h = matrix.length;
        const w = matrix[0].length;
        // console.log(h);
        // console.log(w);

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
        const delta = (max - min)/14;
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

        // console.log(A);
        // 创建canvas并进行绘图
        const canvas = document.createElement("canvas");
        canvas.width = w;
        canvas.height = h;
        const ctx = canvas.getContext('2d');

        // ctx.createImageData(w,h);
        var imgdata = ctx.createImageData(w,h);
        var data = imgdata.data;
        for(let x=0;x<h;x+=1) {
            for(let y=0;y<w;y+=1) {
                let i = 4*(x*w+y);
                data[i] = A[x][y][0];
                data[i+1] = A[x][y][1];
                data[i+2] = A[x][y][2];
                data[i+3] = 255;
            }
        }
        // console.log(data);

        ctx.putImageData(imgdata,0,0);

        const base64Image = canvas.toDataURL('image/jpeg');
        this.setState({
            imgSrc: base64Image
        });

    }

    onHandleClick() {
        const { data, max, min } = this.props;
        this.Mat2Img(data, max, min);
    }

    render() {
        const { visible } = this.props;
        const { imgSrc } = this.state;

        return (
            <div style={{display: visible, paddingTop: '30px'}} >
                <Button style={{margin:'auto'}} onClick={()=>{this.onHandleClick()}}>展示</Button> 
                <img src={imgSrc} width={'1000px'}/>
            </div>
        )
    }
}

export default Cloudmap;