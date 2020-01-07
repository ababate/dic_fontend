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
    this.Cache.lambda = this.state.lambda;
    this.Cache.beta = this.state.beta;
    this.Cache.relevanceThreshold = this.state.relevanceThreshold;
    this.Cache.pyramid_levels = this.state.pyramid_levels;
    this.Cache.pyramid_factor = this.state.pyramid_factor;
    this.Cache.convergenceAccuracy = this.state.convergenceAccuracy;
    this.Cache.coreNumber = this.state.coreNumber;
    this.Cache.spaRes = this.state.spaRes;
    this.emit('setParams', this.Cache);
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
            placeholder = {'λ(lambda)     ' + 'default: ' + 40}
            onChange={e => {this.state.lambda=e.target.value}}
           // value={this.Cache.lambda}
            defaultValue={this.state.lambda}
          />
          </label>
          
          <label>
          β <Input
            className={Style.input}
            placeholder = {'β(beta)     ' + 'default: ' + 0.01}
            onChange={e => {this.state.beta=e.target.value}}
            //value={this.Cache.beta}
            defaultValue={this.state.beta}
          />
          </label>
          
          <label>
          Pyramid Levels <Input
            className={Style.input}
            //placeholder='n(pyramid_levels)'
            placeholder = {'n(pyramid_levels)     ' + 'default: ' + 6}
            onChange={e => {this.state.pyramid_levels=e.target.value}}
            //value={this.Cache.pyramid_levels}
            defaultValue={this.state.pyramid_levels}
          />
          </label>
          
          <label>
          Pyramid Factor <Input
            className={Style.input}
            //placeholder='γ(pyramid_factor)'
            placeholder = {'n(pyramid_factor)     ' + 'default: ' + 0.5}
            onChange={e => {this.state.pyramid_factor=e.target.value}}
            //value={this.Cache.pyramid_factor}
            defaultValue={this.state.pyramid_factor}
          />
          </label>
          
          <label>
          Convergence Accuracy <Input
            className={Style.input}
            //placeholder={lg.convergenceAccuracy}
            placeholder = {'n(convergenceAccuracy)     ' + 'default: ' + 0.001}
            onChange={e => {this.state.convergenceAccuracy=e.target.value}}
            // value={this.Cache.convergenceAccuracy}
            defaultValue={this.state.convergenceAccuracy}
          />
          </label>
          
          <label>
          Core Number <Input
            className={Style.input}
            //placeholder={lg.coreNumber}
            placeholder = {'n(coreNumber)     ' + 'default: ' + 1}
            onChange={e => {this.state.coreNumber=e.target.value}}
            // value={this.Cache.coreNumber}
            defaultValue={this.state.coreNumber}
          />
          </label>
          
          <label>
          Relevance Threshold <Input
            className={Style.input}
            //placeholder={lg.relevanceThreshold}
            placeholder = {'n(relevanceThreshold)     ' + 'default: ' + 0.001}
            onChange={e => {this.state.relevanceThreshold=e.target.value}}
            // value={this.Cache.relevanceThreshold}
            defaultValue={this.state.relevanceThreshold}
          />
          </label>
          
          <label>
          Spacial Resolution <Input
            className={Style.input}
            //placeholder={lg.relevanceThreshold}
            placeholder = {'n(Spacial Resolution)     ' + 'default: ' + 5}
            onChange={e => {this.state.spaRes=e.target.value}}
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