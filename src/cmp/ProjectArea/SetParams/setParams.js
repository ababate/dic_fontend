import React from 'react';
import Component from '../../../core/MyComponent';
import Style from "./setParams.module.css";
import {
  Input,
  Modal
} from 'antd';

class SetParams extends Component {

  constructor() {
    super();
    this.state = {
      visible: false,
      lambda: '40',
      beta: '0.01',
      relevanceThreshold: '0.001',
      pyramid_levels: '6',
      pyramid_factor: '0.5',
      convergenceAccuracy: '0.001',
      coreNumber: '1',
      spaRes: '5'
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
    this.emit('setParams', this.Cache);
  };

  render() {
    const {
      lg
    } = this.props;
    const {
      beta,
      lambda
    } = this.state;
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
            placeholder = {'λ(lambda)     ' + 'default: ' + this.state.lambda}
            onChange={e => {this.Cache.lambda=e.target.value}}
           // value={this.Cache.lambda}
            defaultValue={this.state.lambda}
          />
          </label>
          
          <label>
          β <Input
            className={Style.input}
            placeholder = {'β(beta)     ' + 'default: ' + this.state.beta}
            onChange={e => {this.Cache.beta=e.target.value}}
            //value={this.Cache.beta}
            defaultValue={this.state.beta}
          />
          </label>
          
          <label>
          Pyramid Levels <Input
            className={Style.input}
            //placeholder='n(pyramid_levels)'
            placeholder = {'n(pyramid_levels)     ' + 'default: ' + this.state.pyramid_levels}
            onChange={e => {this.Cache.pyramid_levels=e.target.pyramid_levels}}
            //value={this.Cache.pyramid_levels}
            defaultValue={this.state.pyramid_levels}
          />
          </label>
          
          <label>
          Pyramid Factor <Input
            className={Style.input}
            //placeholder='γ(pyramid_factor)'
            placeholder = {'n(pyramid_factor)     ' + 'default: ' + this.state.pyramid_factor}
            onChange={e => {this.Cache.pyramid_factor=e.target.pyramid_factor}}
            //value={this.Cache.pyramid_factor}
            defaultValue={this.state.pyramid_factor}
          />
          </label>
          
          <label>
          Convergence Accuracy <Input
            className={Style.input}
            //placeholder={lg.convergenceAccuracy}
            placeholder = {'n(convergenceAccuracy)     ' + 'default: ' + this.state.convergenceAccuracy}
            onChange={e => {this.Cache.convergenceAccuracy=e.target.convergenceAccuracy}}
            // value={this.Cache.convergenceAccuracy}
            defaultValue={this.state.convergenceAccuracy}
          />
          </label>
          
          <label>
          Core Number <Input
            className={Style.input}
            //placeholder={lg.coreNumber}
            placeholder = {'n(coreNumber)     ' + 'default: ' + this.state.coreNumber}
            onChange={e => {this.Cache.coreNumber=e.target.coreNumber}}
            // value={this.Cache.coreNumber}
            defaultValue={this.state.coreNumber}
          />
          </label>
          
          <label>
          Relevance Threshold <Input
            className={Style.input}
            //placeholder={lg.relevanceThreshold}
            placeholder = {'n(relevanceThreshold)     ' + 'default: ' + this.state.relevanceThreshold}
            onChange={e => {this.Cache.relevanceThreshold=e.target.relevanceThreshold}}
            // value={this.Cache.relevanceThreshold}
            defaultValue={this.state.relevanceThreshold}
          />
          </label>
          
          <label>
          Spacial Resolution <Input
            className={Style.input}
            //placeholder={lg.relevanceThreshold}
            placeholder = {'n(Spacial Resolution)     ' + 'default: ' + this.state.spaRes}
            onChange={e => {this.Cache.spaRes=e.target.spaRes}}
            // value={this.Cache.spaRes}
            defaultValue={this.state.spaRes}
          />
          </label>

        </div>
      </Modal>
    );
  }
}

export default SetParams;