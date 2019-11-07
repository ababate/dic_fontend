import React from 'react';
import Component from '../../../core/MyComponent';
import Style from "./setParams.module.css";
import { Input, Modal } from 'antd';

class SetParams extends Component {

  constructor() {
    super();
    this.state = {
      visible: false,
      lambda: '',
      beta: '',
      relevanceThreshold: '',
      pyramid_levels: '',
      pyramid_factor: '',
      convergenceAccuracy: '',
      coreNumber: '',
      spaRes: ''
    }
  }

  componentDidMount() {
    this.on('nav_setParams', () => {
      this.setState({
        visible: true,
      });
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
    });
    this.emit('setParams',this.Cache);
  };

  render() {
    const { lg } = this.props;
    return (
      <Modal
        title={lg.setParam}
        visible={this.state.visible}
        okText={lg.OK}
        onOk={this.handleOk}
        onCancel={this.handleCancel}
        cancelText={lg.cancel}
      >

        <div className={Style.form}>
        
          <label>
          λ <Input
            className={Style.input}
            //placeholder='λ(lambda)'
            onChange={e => {this.setState({lambda: this.Cache.lambda=e.target.value})}}
            value={this.Cache.lambda}
            defaultValue={1}
          />
          </label>
          
          <label>
          β <Input
            className={Style.input}
            //placeholder='β(beta)'
            onChange={e => {this.setState({beta: this.Cache.beta=e.target.value})}}
            value={this.Cache.beta}
            defaultValue={1}
          />
          </label>
          
          <label>
          Pyramid Levels <Input
            className={Style.input}
            //placeholder='n(pyramid_levels)'
            onChange={e => {this.setState({pyramid_levels: this.Cache.pyramid_levels=e.target.value})}}
            value={this.Cache.pyramidLevels}
            defaultValue={1}
          />
          </label>
          
          <label>
          Pyramid Factor <Input
            className={Style.input}
            //placeholder='γ(pyramid_factor)'
            onChange={e => {this.setState({pyramid_factor: this.Cache.pyramid_factor=e.target.value})}}
            value={this.Cache.pyramidFactor}
            defaultValue={1}
          />
          </label>
          
          <label>
          Convergence Accuracy <Input
            className={Style.input}
            //placeholder={lg.convergenceAccuracy}
            onChange={e => {this.setState({convergenceAccuracy: this.Cache.convergenceAccuracy=e.target.value})}}
            value={this.Cache.convergenceAccuracy}
            defaultValue={1}
          />
          </label>
          
          <label>
          Core Number <Input
            className={Style.input}
            //placeholder={lg.coreNumber}
            onChange={e => {this.setState({coreNumber: this.Cache.coreNumber=e.target.value})}}
            value={this.Cache.coreNumber}
            defaultValue={1}
          />
          </label>
          
          <label>
          Relevance Threshold <Input
            className={Style.input}
            //placeholder={lg.relevanceThreshold}
            onChange={e => {this.setState({relevanceThreshold: this.Cache.relevanceThreshold=e.target.value})}}
            value={this.Cache.relevanceThreshold}
            defaultValue={1}
          />
          </label>
          
          <label>
          Spacial Resolution <Input
            className={Style.input}
            //placeholder={lg.relevanceThreshold}
            onChange={e => {this.setState({spaRes: this.Cache.spaRes=e.target.value})}}
            value={this.Cache.spaRes}
            defaultValue={1}
          />
          </label>

        </div>
      </Modal>
    );
  }
}

export default SetParams;