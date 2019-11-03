let ENV = 'dev';
// 配置
export const Config = {};
Config.urlRoot = '1';
// 基础数据访问
export const baseFetch = (option) => {
  option = Object.assign({}, {
    url: '',
    type: '',
    data: null,
    success: () => {},
    error: () => {}
  }, option);
  let setting = {
    method: option.type.toUpperCase(),
    // mode: "no-cors",
    credentials: 'include',
    headers: {
      // 'Access-Control-Allow-Credentials': true,
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      // 'Access-Control-Allow-Origin': '*'
    },
  }
  if (option.data != null) {
    setting.body = JSON.stringify(option.data);
  }
  // if (ENV == 'dev')
  // option.url = '/imagecul' + option.url;
  fetch(option.url, setting).then((res) => {

      if (res.status !== 200) {
        option.error(res);
        return null;
      }
      return res.json()
    })
    .then((data) => {
      option.success(data)
    })
}

// 异步上传文件，接受json
export const baseFetchFile = (option) => {
  option = Object.assign({}, {
    url: '',
    formData: {},
    success: () => {},
    error: () => {}
  }, option);
  let setting = {
    method: 'POST',
    credentials: 'include',
    body: option.formData
  };
  // option.url = '/imagecul' + option.url;
  fetch(option.url, setting).then(
      (res) => {
        if (res.status !== 200) {
          option.error(res);
          return null;
        }
        return res.json()
      }
    )
    .then((data) => {
      option.success(data);
    })
}

// 请求数据，返回的是字符串
export const baseFetchText = (option) => {
  option = Object.assign({}, {
    url: '',
    type: '',
    data: null,
    success: () => {},
    error: () => {}
  }, option);
  let setting = {
    method: option.type.toUpperCase(),
    // mode: "no-cors",
    credentials: 'include',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
  }
  if (option.data != null) {
    setting.body = JSON.stringify(option.data);
  }
  // option.url = '/imagecul' + option.url;
  fetch(option.url, setting).then(
      (res) => {
        if (res.status !== 200) {
          option.error(res);
          return null;
        }
        return res.text()
      }
    )
    .then((data) => {
      option.success(data)
    })
}

// 对象的深拷贝
export const deepCopy = (o) => {
  if (o instanceof Array) {
    var n = [];
    for (var i = 0; i < o.length; ++i) {
      n[i] = deepCopy(o[i]);
    }
    return n;
  } else if (o instanceof Function) {
    var n = new Function("return " + o.toString())();
    return n
  } else if (o instanceof Object) {
    var n = {}
    for (var i in o) {
      n[i] = deepCopy(o[i]);
    }
    return n;
  } else {
    return o;
  }
}

// 设置cookie
export const setCookie = (name, value) => {
  var Days = 30;
  var exp = new Date();
  exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
  document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ';path=/;';
}

// 获取cookie
export const getCookie = (name) => {
  var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

  if (arr = document.cookie.match(reg))

    return unescape(arr[2]);
  else
    return null;
}

// 删除cookie
export const delCookie = (name) => {
  var exp = new Date();
  exp.setTime(exp.getTime() - 1);
  var cval = getCookie(name);
  if (cval != null)
    document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

// 图片压缩
// 使用方法
// Utils.compressImg({
//   file:this.state.fileList1[0],
//   success:(file,name)=>{
//     formData.append("trademarks",file,name);
//   }
// })
export const compressImg = (option) => {
  let reader = new FileReader();
  let img = new Image();
  let file = option.file;
  let success = option.success;
  if (file == null || success == null) {
    return;
  }

  let canvas = document.createElement('canvas');
  let context = canvas.getContext('2d');

  img.onload = function () {
    let originWidth = this.width;
    let originHeight = this.height;
    //最大尺寸限制
    let maxWidth = 2000,
      maxHeight = 2000;
    //目标尺寸
    let targetWidth = originWidth,
      targetHeight = originHeight;
    //图片尺寸超出限制
    if (originWidth > maxWidth || originHeight > maxHeight) {
      if (originWidth / originHeight > maxWidth / maxHeight) {
        // 更宽，按照宽度限定尺寸
        targetWidth = maxWidth;
        targetHeight = Math.round(maxWidth * (originHeight / originWidth));
      } else {
        targetHeight = maxHeight;
        targetWidth = Math.round(maxHeight * (originWidth / originHeight));
      }
    }
    // canvas对图片进行缩放
    canvas.width = targetWidth;
    canvas.height = targetHeight;
    // 清除画布
    context.clearRect(0, 0, targetWidth, targetHeight);
    // 图片压缩
    context.drawImage(img, 0, 0, targetWidth, targetHeight);
    //2进制
    canvas.toBlob(function (blob) {
      success(blob, file.name);
    }, file.type || 'image/png');
  }
  // 文件base64化，以便获知图片原始尺寸
  reader.onload = function (e) {
    img.src = e.target.result;
  };
  //加载图片
  reader.readAsDataURL(file);
}

export const urlParams = () => {
  const obj = {};
  const url = window.location.href;
  const p = url.split('?');
  if (p.length !== 2) return;
  const pp = p[1].split('&');
  pp.map(v => {
    const a = v.split('=');
    if (a.length === 2) {
      obj[a[0]] = a[1];
    }
  })
  return obj;
}

class Event {
  constructor() {
    this.handlers = {};
  }

  // 添加注册类的储存
  on(eventName, handler, className){
    if(!(eventName in this.handlers)) {
      this.handlers[eventName] = [];
    }
    this.handlers[eventName].push({handler,className});
  }

  emit(eventName){
    const handlerArgs = Array.prototype.slice.call(arguments,1);
    if (!(eventName in this.handlers)) return;
    // if (handlerArgs.length == 0) {
    //   this.handlers[eventName](...handlerArgs);
    // } else {
    //   this.handlers[eventName](...handlerArgs);
    // }
    this.handlers[eventName].forEach(obj => {
      if (obj['handler']) {
        obj['handler'](...handlerArgs);

      }
    });
  }
  show = () => {
    for(let key in this.handlers) {
      const objs = this.handlers[key];
      objs.forEach( v => {
      } )
    }
  }
  // 删除这个类里面名字叫className的监听
  off(eventName, className) {
    if(eventName in this.handlers) {
      let arr = [];
      this.handlers[eventName].forEach( (v, index) => {
        if (v.className === className) arr.push(index);
      });
      arr.reverse();
      for (let i = 0; i < arr.length; i++) {
        this.handlers[eventName].splice(arr[i], 1);
      }
    }
  }
  
} 
export const MyEvent = new Event();
export const Cache = {};