import React from 'react';
import Component from '../../core/MyComponent';
import Style from './folder.module.css';

class Folder extends Component {
  constructor(props) {
    super(props);
    this.state = {
      // isOpen: true,
      // folderName: props.data.name,
      // folderId: props.data.id,
    }
  }

  changeOpenState = () =>{
    // this.setState({
    //   isOpen: !this.state.isOpen,
    // })
    if(this.props.onChange) {
      this.props.onChange(this.props.data);
    }
  }

  render() {
    // const { isOpen } = this.state;
    const { name, id } = this.props.data;
    const { isOpen } = this.props;
    const { className } = this.props;
    return (
      <div className={className}>
        <img className={Style.img} src={isOpen ? './images/folder-open.png' : './images/folder-close.png'} onClick={()=>{this.changeOpenState()}}/>
        <div className={Style.text}>{name}</div>
      </div>

    )
  }
}

export default Folder;