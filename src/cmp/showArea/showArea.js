import React from 'react';
import Component from '../../core/MyComponent';
import Folder from './folder';
import Style from './showArea.module.css';
import { Select, Input } from 'antd';
// import Plot from 'react-plotly.js';

import Cloudmap from './cloudmap';

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
            folderData: res.data,
          })
        }
      }
    })
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
          })
        }
        else {
          this.setState({
            visible: 'none',
            matData: [],
          })
        }
      }
      return v;
    });
    
    this.setState({
      folderData
    })
  }

  onhandleChange(value) {
    const { matData } = this.state;
    this.setState({
      showData: matData[value]
    })
  }

  render() {
    const { lg } = this.props;
    const { folderData, visible, matData, showData, max, min } = this.state;
    let items = folderData.map( (v, index) => {
      return (<Folder key={index} className={Style.folder} data={v} isOpen={v.isOpen} onChange={this.folderOnChange.bind(this)} />)
    });
    // let colorscaleValue = [
    //   // [0, 'rgb(137,0,255)'],
    //   // [1/15, 'rgb(0,92,255)'],
    //   // [2/15, 'rgb(0,165,255)'],
    //   // [3/15, 'rgb(0,209,255)'],
    //   // [4/15, 'rgb(0,248,255)'],
    //   // [5/15, 'rgb(0,255,233)'],
    //   // [6/15, 'rgb(0,255,192)'],
    //   // [7/15, '#00FF8D'],
    //   // [8/15, '#49FF00'],
    //   // [9/15, '#A4FF00'],
    //   // [10/15,'#D1FF00'],
    //   // [11/15,'#F5FF00'],
    //   // [12/15,'#FFED00'],
    //   // [13/15,'#FFC600'],
    //   // [14/15,'#FF9500'],
    //   // [1,'rgb(255,0,0)'],
    // ]
    // let plotdata = [{
    //   z: showData,
    //   type: 'heatmap',
    //   colorscale: 'Viridis',
    // }]
    // let layout = {
    //   xaxis: axisTemplate,
    //   yaxis: axisTemplate,
    // }
    
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
            <Select defaultValue={'choose a variable'} style={{display: visible, width: '120px'}} onChange={this.onhandleChange.bind(this)}>
              <Option value={'resultU'}>U</Option>
              <Option value={'resultV'}>V</Option>
              <Option value={'resultExx'}>Exx</Option>
              <Option value={'resultExy'}>Exy</Option>
              <Option value={'resultEyy'}>Eyy</Option>
              <Option value={'resultEf'}>E1</Option>
              <Option value={'resultFs'}>E2</Option>
            </Select>
            <Input placeholder='MaxValue' onChange={e => {this.setState({max: e.target.value})}} style={{display: visible}}/>
            <Input placeholder='MinValue' onChange={e => {this.setState({min: e.target.value})}} style={{display: visible}}/>
            <Cloudmap data={showData} visible={visible} max={max} min={min}/>
            {/* cloudmap */}
            {/* <Plot 
              data={plotdata} 
              layout={layout} 
            /> */}
          </div>
        </div>

      </div>

    )
  }
}

export default ShowArea;