import React from 'react';
import Component from '../core/MyComponent';
import Style from './pages.module.css';

import ShowArea from '../cmp/showArea/showArea';

//import LoginModal from '../cmp/login/login';
//import RegisterModal from '../cmp/login/register';
//import SetParams from "../cmp/data/setParams";
//import SelectAlgorithm from "../cmp/data/selectAlgorithm"
//import ChoosePoint from  "../cmp/data/choosePoint"
import ProjectArea from "../cmp/ProjectArea/projectArea";
//import NewProject from "../cmp/ProjectArea/newproject/newProject";
class Pages extends Component {
  
  render() {
    const { pageName, lg } = this.props;
    return (
      <div style={{height: '100%'}}>

        {/*项目展示区*/}
        <div className={pageName==='project' ? '' : Style.hide} style={{height: '100%'}}><ProjectArea lg={lg}/></div>
        {/*操作区*/}
        {/*<div className={pageName==='action' ? '' : Style.hide} style={{height: '100%'}}><ActionArea lg={lg}/></div>*/}
        {/*展示区 */}
        <div className={pageName==='show' ? '' : Style.hide} style={{height: '100%'}}><ShowArea lg={lg}/></div>
        {/*<div className={pageName==='choose' ? '' : Style.hide} style={{height: '100%'}}><ChoosePoint lg={lg}/></div>*/}
        {/*<LoginModal lg={lg}/>
        <RegisterModal lg={lg}/>
        <SetParams lg={lg}/>
        <SelectAlgorithm lg={lg}/>*/}
      </div>
    )
  }
}
export default Pages;