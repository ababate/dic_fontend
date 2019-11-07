import React from 'react';
import Component from '../../core/MyComponent';
import Style from './footer.module.css';
import { Progress } from 'antd';

class Step extends Component {
	constructor() {
		super();
		this.state = {
			Info: '',
      pre: 0,
      percent: 0,
      status: 'active',
		};
	}

  componentWillUnmount() {
    this.off('nav_run');
    //console.log(this.Utils.MyEvent.handlers);

    clearInterval(this.intervalId);
  }
  
  componentDidMount() {
    this.on('nav_run', () => { 
    	//this.setState({
    		//Info: 'Submitted',
    	//})
      //
      // console.log('req', this);
      this.Utils.baseFetch({
      type: 'GET',
      url: '/imagecul/task/run',
      success: res => {
        if (res.code==200) {
          this.setState({
            Info: 'Submmitted',
            percent: 30,
            status: 'active',
          })
      }
    }
    });

      this.timer = setInterval(() => { 
        this.Utils.baseFetch({
            type: 'GET',
            url:'/imagecul/task/qury',
            success: res => {
              let data = res.data;
              let { Info, pre } = this.state;
              let current = data.length;
                if (Info=='Submmitted'&&current !== pre+1) {
                      this.setState({
                         Info: 'Processing...',
                         percent: 60,
                      })
                };
                if (current == pre+1){
                  if (data[current-1].status=='COMPLETED') {
                      this.setState({
                         Info: 'Calculation Completed',
                         percent: 100,
                         status: 'success',
                       })
                  }
                  else if (data[current-1].status=='FAIL') {
                      this.setState({
                         Info: 'Calculation Failed',
                         percent: 100,
                         status: 'exception',
                       })
                  }   
                };
              this.setState({
                pre: current,
              })
            }
          })
     }, 5000)
    })
    //
    //
    }

  render() {
  	return (
  	  <div className={ Style.steps } >
  	     <Progress percent={this.state.percent} 
                   format={percent => percent + '% ' + this.state.Info} 
                   status={this.state.status}
                   />
  	  </div>
  	)
  }
}
export default Step;