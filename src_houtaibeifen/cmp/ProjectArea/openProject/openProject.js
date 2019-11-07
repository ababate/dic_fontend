import React from 'react';
import { Modal, List, } from 'antd';
import Component from '../../../core/MyComponent';

class OpenProject extends Component {

  constructor(props) {
    super(props);
    this.state = {
      projectData: [],
      projectName: '',
      visible: false,
    }
  }
  componentWillUnmount() {
    this.off('nav_open');
  }
  componentDidMount() {
    this.on('nav_open', () => {
      this.setState({
        visible: true,
      });
      //请求项目数据
      this.Utils.baseFetch({
        type: 'GET',
        url: '/imagecul/project/get/user',
        success: res => {
          if(res.code === 200){
            this.setState({
              projectData: res.data,
            })
          }
        }
      })
    })

  }
  handleCancel = () => {
    this.setState({
      visible: false,
    })
  };
  handleOk = () => {
    // this.Utils.baseFetch({
    //   type: 'GET',
    //   url: '/imagecul/project/create?name='+this.state.projectName,
    //   success: res => {
    //     console.log(res);
        //创建成功后，刷新项目列表
        // this.props.onProjectAdd(res.data);
        this.setState({
          visible: false
        })
      }

  handleOnclick = (projectId) => {
    console.log(projectId);
    this.state.projectData.map(value => {
      if(value.id === projectId){
          this.props.onProjectChange(value);
      }
    });
    this.setState({
      visible: false
    })
  };

  render() {
    const { lg } = this.props;
    return (
      <Modal
        title={lg.open}
        visible={this.state.visible}
        okText={lg.OK}
        onOk={this.handleOk}
        onCancel={this.handleCancel}
        cancelText={lg.cancel}
      >
        <List
          // grid={{
          //   gutter: 16, xs: 1, sm: 2, md: 4, lg: 4, xl: 6, xxl: 3,
          // }}
          dataSource={this.state.projectData}
          renderItem={item => (
            <List.Item>
             <div style={{cursor: 'pointer'}} onClick={()=>{this.handleOnclick(item.id)}}>{item.name}</div>
            </List.Item>
          )}
        />

      </Modal>
    )
  }
}

export default OpenProject;