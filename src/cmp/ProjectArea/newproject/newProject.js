import React from 'react';
import { Modal, Input, } from 'antd';
import Component from '../../../core/MyComponent';

class NewProject extends Component {

  constructor(props) {
    super(props);
    this.state = {
      projectName: '',
      visible: false,
    }
  }
  componentWillUnmount() {
    this.off('nav_new');
  }
  componentDidMount() {
    this.on('nav_new', () => {
      this.setState({
        visible: true,
      })
    })

  }
  handleCancel = () => {
    this.setState({
      visible: false,
    })
  };
  handleOk = () => {
    this.Utils.baseFetch({
      type: 'GET',
      url: '/imagecul/project/create?name='+this.state.projectName,
      success: res => {
        console.log(res);
        //创建成功后，刷新项目列表
        this.props.onProjectAdd(res.data);
        this.setState({
          visible: false
        })
      }
    })
  };
  render() {
    const { lg } = this.props;
    return (
      <Modal
        title={lg.newProject}
        visible={this.state.visible}
        okText={lg.OK}
        onOk={this.handleOk}
        onCancel={this.handleCancel}
        cancelText={lg.cancel}
      >
        <Input
          placeholder={lg.inputProjectName}
          onChange={e => {this.setState({projectName: e.target.value})}}
        />
      </Modal>
    )
  }
}

export default NewProject;