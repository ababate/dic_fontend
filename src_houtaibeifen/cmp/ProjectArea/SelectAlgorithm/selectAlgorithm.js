import React from 'react';
import Component from '../../../core/MyComponent';
import Style from "./selectAlgorithm.module.css";
import {Modal,Radio} from 'antd';

const RadioGroup = Radio.Group;

class SelectAlgorithm extends Component {
  constructor() {
    super();
    this.state = {
      algorithm: this.Cache.algorithm,
    }
  }

  componentDidMount() {
    this.on('nav_chooseAlgorithm', () => {
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
    this.setState({
      visible: false,
    })
    this.emit('setParams',this.Cache);
  };

  onChange = (e) => {
    this.Cache.algorithm = e.target.value;
    this.setState({
      algorithm: e.target.value,
    });
  };

  render() {
    const { lg } = this.props;
    return (
      <div>
        <Modal
          title={lg.chooseAlgorithm}
          visible={this.state.visible}
          okText={lg.OK}
          onOk={this.handleOk}
          onCancel={this.handleCancel}
          cancelText={lg.cancel}
        >
          <RadioGroup onChange={this.onChange} value={this.state.algorithm}>
            <Radio value="Accurate TV-L1">Accurate TV-L1</Radio>
            <Radio value="Fast TV-L1">Fast TV-L1</Radio>
          </RadioGroup>
        </Modal>
      </div>
    );
  }
}

export default SelectAlgorithm;