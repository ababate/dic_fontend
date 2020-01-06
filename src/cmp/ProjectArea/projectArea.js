import React from 'react';
import {
  Upload,
  Icon,
  Modal,
  message,
  List
} from 'antd';
import Component from '../../core/MyComponent';
import Folder from './folder';
import Image from './image';
import Style from './projectArea.module.css';

import NewProject from "./newproject/newProject";
import OpenProject from "./openProject/openProject";
import ChooseDomain from "./ChooseDomain/ChooseDomain";
import LoginModal from './Login/login';
import RegisterModal from './Login/register';
import SetParams from "./SetParams/setParams";
import SelectAlgorithm from "./SelectAlgorithm/selectAlgorithm";
import Histogram from "./Histogram/histogram";

// let data = require('./json/folder.json');
class ProjectArea extends Component {
  constructor() {
    super();
    this.state = {
      projectData: [],
      curProjectInfo: {},
      imageData: [],
      fileA: {},
      fileB: {},
      curImage: '',
      croppedImg: '',
    }
  }

  componentWillUnmount() {
    const arr = ['nav_open', 'nav_chooseImage', 'nav_deleteImage', 'nav_chooseSpecial', 'nav_chooseAlgorithm', 'nav_setParams', 'nav_run'];
    arr.forEach(v => {
      this.off(v);
    })
  }

  isContain(arr, v) {
    for (var i = 0; i < arr.length; i++) {
      if (arr[i] === v) return true;
    }
    return false;
  }

  requestProjectData = () => {
    //请求项目列表
    this.Utils.baseFetch({
      type: 'GET',
      url: '/imagecul/project/get/user',
      success: res => {
        if (res.code === 200) {

          let curProject = {};
          res.data.forEach(function(v, index) {

            if (v.status === "OPEN")
              curProject = v;
          });

          // 获取项目的图片
          this.Utils.baseFetch({
            type: 'GET',
            url: '/imagecul/project/list/images?id=' + curProject.id,
            success: res => {
              if (res.code === 200) {
                res.data.map((v, index) => {
                  if (this.isContain(curProject.referenceImages, v.id)) {
                    this.setState({
                      fileA: v,
                    })
                  }
                  if (this.isContain(curProject.deformedImages, v.id)) {
                    this.setState({
                      fileB: v,
                    })
                  }
                });
                this.setState({
                  imageData: res.data,
                })
              }
            }
          });

          this.setCache(curProject);
          this.setState({
            projectData: res.data,
            curProjectInfo: curProject
          })
        }
      }
    });
  };

  componentDidMount() {
    //新建项目
    this.on('new', (obj) => {
      if (obj.pageName !== 'project') {
        this.emit('changePage', 'project');
      }
      this.setState({
        isDelete: false,
        imageCanEdit: true,
      })
    });
    //打开项目
    this.on('open', (obj) => {
      if (obj.pageName !== 'project') {
        this.emit('changePage', 'project');
      }
    });

    this.on('nav_referenceImage', (obj) => {
      if (obj.pageName !== 'project') {
        this.emit('changePage', 'project');
      }
      console.log("A");
      this.setState({
        curImage: 'A',
        fileA: '',
      })
    });

    this.on('nav_deformedImage', (obj) => {
      if (obj.pageName !== 'project') {
        this.emit('changePage', 'project');
      }
      console.log("B");
      this.setState({
        curImage: 'B',
        fileB: '',
      })

    });

    this.on('nav_chooseAlgorithm', (obj) => {
      if (obj.pageName !== 'project') {
        this.emit('changePage', 'project');
      }
    });

    this.on('nav_run', (obj) => {
      if (obj.pageName !== 'project') {
        this.emit('changePage', 'project');
      }
      console.info(1, this.Cache);
      this.Utils.baseFetch({
        type: 'GET',
        url: '/imagecul/task/run',
        success: res => {
          if (res.code === 200) {
            console.log(res.data);
          }
        }
      });
    });

    this.on('setParams', (obj) => {
      const {
        lambda,
        beta,
        pyramid_levels,
        pyramid_factor,
        convergenceAccuracy,
        coreNumber,
        relevanceThreshold,
        algorithm,
        spaRes,
        domainX,
        domainY,
        domainW,
        domainH,
        croppedImg,
      } = obj;

      let project = this.state.curProjectInfo;
      project.lambda = lambda;
      project.beta = beta;
      project.pyramidLevels = pyramid_levels;
      project.pyramidFactor = pyramid_factor;
      project.convergenceAccuracy = convergenceAccuracy;
      project.coreNumber = coreNumber;
      project.relevanceThreshold = relevanceThreshold;
      project.algorithm = algorithm;
      project.spaRes = spaRes;
      project.domainX = domainX;
      project.domainY = domainY;
      project.domainW = domainW;
      project.domainH = domainH;

      this.setState({
        curProjectInfo: project,
        croppedImg: croppedImg,
      });

      // 参数信息持久化到数据库
      /*var json = [];
      json.push(obj);*/

      let preparams = {};
      preparams.lambda = lambda;
      preparams.beta = beta;
      preparams.pyramidLevels = pyramid_levels;
      preparams.pyramidFactor = pyramid_factor;
      preparams.convergenceAccuracy = convergenceAccuracy;
      preparams.coreNumber = coreNumber;
      preparams.relevanceThreshold = relevanceThreshold;
      preparams.algorithm = algorithm;
      preparams.spaRes = spaRes;
      preparams.domainX = domainX;
      preparams.domainY = domainY;
      preparams.domainW = domainW;
      preparams.domainH = domainH;

      var params = JSON.stringify(preparams).replace("{", "dd").replace("}", "bb");
      this.Utils.baseFetch({
        type: 'GET',
        url: '/imagecul/project/setParams?params=' + params,
        success: res => {
          if (res.code === 200) {
            console.log(res.data);
          }
        }
      });

    });

    this.on('login', this.requestProjectData);
  }

  // 替换中心的图片
  setImage(image) {
    const {
      curImage
    } = this.state;
    if (curImage === 'A') {
      this.setState({
        fileA: image,
      });

      // 保存图片
      this.Utils.baseFetch({
        type: 'GET',
        url: '/imagecul/project/saveImage?type=A&imageid=' + image.id,
        success: res => {
          if (res.code === 200) {
            console.log(res.data);
          }
        }
      });

    }
    if (curImage === 'B') {
      this.setState({
        fileB: image,
      });

      this.Utils.baseFetch({
        type: 'GET',
        url: '/imagecul/project/saveImage?type=B&imageid=' + image.id,
        success: res => {
          if (res.code === 200) {
            console.log(res.data);
          }
        }
      });
    }
  }

  resetImage(img) {
    this.setState(
      img === 'A' ? {
        fileA: {}
      } : {
        fileB: {}
      }
    )
  }

  handleChange(info) {
    if (info.file.status === 'done') {
      //文件上传完成，将返回文件url添加到imagedate中
      const imageInfo = info.file.response.data;
      this.setState({
        imageData: [...this.state.imageData, imageInfo]
      })
    } else if (info.file.status === 'error') {
      message.error(`${info.file.name} file upload failed.`);
    }
  }

  onProjectAdd(projectData) {
    this.setState({
      projectData: [...this.state.projectData, projectData],
    })
    this.onProjectChange(projectData);
  }

  onProjectChange(project) {

    // 更改项目状态信息
    this.Utils.baseFetch({
      type: 'GET',
      url: '/imagecul/project/open?id=' + project.id,
      success: res => {
        if (res.code === 200) {
          console.log(res.data);
        }
      }
    });
    // 获取项目的图片
    this.Utils.baseFetch({
      type: 'GET',
      url: '/imagecul/project/list/images?id=' + project.id,
      success: res => {
        if (res.code === 200) {
          let fileA = {};
          let fileB = {};
          res.data.map((v, index) => {
            if (this.isContain(project.referenceImages, v.id)) {
              fileA = v;
            }
            if (this.isContain(project.deformedImages, v.id)) {
              fileB = v;
            }
          });
          this.setState({
            imageData: res.data,
            fileA: fileA,
            fileB: fileB,
            curProjectInfo: project,
          })
        }
      }
    });

    this.setCache(project);
  }

  setCache(project) {
    this.Cache.lambda = project.lambda;
    this.Cache.beta = project.beta;
    this.Cache.pyramid_levels = project.pyramidLevels;
    this.Cache.pyramid_factor = project.pyramidFactor;
    this.Cache.convergenceAccuracy = project.convergenceAccuracy;
    this.Cache.coreNumber = project.coreNumber;
    this.Cache.relevanceThreshold = project.relevanceThreshold;
    this.Cache.algorithm = project.algorithm;
    this.Cache.spaRes = project.spaRes;
    this.Cache.domainX = project.domainX;
    this.Cache.domainY = project.domainY;
    this.Cache.domainW = project.domainW;
    this.Cache.domainH = project.domainH;
  }

  render() {
    const {
      lg
    } = this.props;
    const {
      curProjectInfo,
      imageData,
      fileA,
      fileB,
      croppedImg
    } = this.state;
    let images = imageData.map((v, index) => {
      return (
        <Image key={index}
               data={v}
               setImage = {this.setImage.bind(this)}
        />
      )
    });

    return (
      <div style={{height: "calc(100% - 2px)"}}>
        {/*左边信息展示区*/}
        {
          typeof (curProjectInfo.name) === "undefined" ?
            (<div className={Style.left}>
                <div className={Style.top}>{lg.projectInfo}</div>
             </div>
            )
            :
            (<div className={Style.left}>
              <div className={Style.top}>{lg.projectInfo}</div>

              <div className={Style.bottom}>

              <div className={Style.maintitle}>{lg.baseProInfo}</div>
              
              <div>{lg.projectName}:</div>
              <div>{curProjectInfo.name}</div>
              
              <div>{lg.createTime}:</div>
              <div>{curProjectInfo.createTime}</div>
              
              <div>{lg.updateTime}:</div>
              <div>{curProjectInfo.updateTime}</div>
              
              <div>{lg.amountOfReferences}:</div>
              <div>{curProjectInfo.referenceNum}</div>
              
              <div>{lg.amountOfDeformeds}:</div>
              <div>{curProjectInfo.deformedNum}</div>
              
              <div>{lg.amountOfTasks}:</div>
              <div>{curProjectInfo.taskNum}</div>
              
              <div>{lg.amountOfResults}:</div>
              <div>{curProjectInfo.resultNum}</div>
              
              <div className={Style.maintitle}>{lg.paramInfo}</div>
              
              <div>λ (Lambda):</div>
              <div>{curProjectInfo.lambda}</div>
              
              <div>β (Beta):</div>
              <div>{curProjectInfo.beta}</div>
              
              <div>n (Pyramid Levels):</div>
              <div>{curProjectInfo.pyramidLevels}</div>
              
              <div>γ (Pyramid Factor):</div>
              <div>{curProjectInfo.pyramidFactor}</div>
              
              <div>{lg.convergenceAccuracy}:</div>
              <div>{curProjectInfo.convergenceAccuracy}</div>
              
              <div>{lg.coreNumber}:</div>
              <div>{curProjectInfo.coreNumber}</div>
              
              <div>{lg.relevanceThreshold}:</div>
              <div>{curProjectInfo.relevanceThreshold}</div>
              
              <div>{lg.algorithm}:</div>
              <div>{curProjectInfo.algorithm}</div>

              <div>{lg.chooseAreaStartPoint}:</div>
              <div>({curProjectInfo.domainX}, {curProjectInfo.domainY})</div>

              <div>{lg.chooseAreaSize}:</div>
              <div>W={curProjectInfo.domainW}, H={curProjectInfo.domainH}</div>
              
              <div>{lg.spacialResolution}</div>
              <div>D={curProjectInfo.spaRes}</div>

              </div>
              
            </div>)
        }

        <div className={Style.right}>
          {/*右上部图片浏览区域*/}
          <div className={Style.top}>
            <div>
              {images}
              <Upload
                action="/imagecul/image/upload"
                listType="picture-card"
                onPreview={this.handlePreview}
                onChange={this.handleChange.bind(this)}
                multiple={true}
                showUploadList = {false}
                name={"image"}
              >
              {typeof (curProjectInfo.name) === "undefined" ? null : 
              (<div>
                  <Icon type="plus" />
                  <div className="ant-upload-text">{lg.uploadfile}</div>
                </div>)}
                
              </Upload>
            </div>
          </div>

          {/*图片展示*/}
          <div className={Style.main}>
            <div className={Style.show}>
              <div className={Style.blockLeft}>
              <img className={fileA===''?Style.hideLeft:Style.mainLeft} src={fileA.originalUrl} onClick={() => {this.resetImage('A')}}/>
              <div className={Style.mainPictureTitle}>REFERENCE IMAGE</div>
              </div>
              <div className={Style.blockRight}>
              <img className={fileB===''?Style.hideRight:Style.mainRight} src={fileB.originalUrl} onClick={() => {this.resetImage('B')}} />
              <div className={Style.mainPictureTitle}>DEFORMED IMAGE</div>
              </div>
            </div>
          </div>
        </div>
        
        {/*项目区功能界面操作*/}
        <NewProject lg={lg} onProjectAdd={this.onProjectAdd.bind(this)}/> {/*添加项目*/}
        <OpenProject lg={lg} onProjectChange={this.onProjectChange.bind(this)}/> {/*打开项目*/}
        <LoginModal lg={lg}/> {/*登录*/}
        <RegisterModal lg={lg}/> {/*注册*/}
        <Histogram lg={lg} RefImg={croppedImg}/> {/*图像灰度直方图*/}

        {/*项目区计算参数操作*/}
        <ChooseDomain lg={lg} fileA={fileA} fileB={fileB}/>
        <SetParams lg={lg}/>
        <SelectAlgorithm lg={lg}/>
        
      </div>
    )
  }
}

export default ProjectArea;