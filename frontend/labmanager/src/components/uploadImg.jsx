import React, { Component } from 'react';
import { Upload, Icon, Modal,message } from 'antd';
import imgzip from '@utils/imgZip'  
function getBase64(file) {
  return new Promise((resolve, reject) => {
    imgzip.photoCompress(file, {quality:1},(base64)=>{
      resolve(base64)
    });
  });
}
class UploadImg extends Component {
  constructor(props){
    super(props)
    let fileListPraent = this.props.urlList;//传过来的List
    let num = this.props.num;//传过来的List
    let fileList = [];
    if(!fileListPraent){
      fileList = []
    }else{
      let lists = JSON.parse(fileListPraent);
      fileList = lists.map((item,index)=>{
        item.uid = `-${index}`
        return item
      });
    }
    this.state={
      fileListPraent,
      fileList,
      previewVisible: false,
      previewImage: '',
      num,
    }
  }
  handleCancel = () => this.setState({ previewVisible: false });

  handlePreview = async file => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj);
    }
    this.setState({
      previewImage: file.url || file.preview,
      previewVisible: true,
    });
  };
  handleChange = async(e) =>{
    const isLt2M = e.file.size / 1024 / 1024 < 3;
    if (!['image/png','image/jpeg'].includes(e.file.type)) {
      message.error('上传的图片不符合要求！');
      return false;
    }
    if (!isLt2M) {
      message.error('图片不能大于3M!');
      return false;
    }
    let { fileList } = e;
    let  nowArr = [];
    let url = "";
    for(let i=0;i<fileList.length;i++){
        if(fileList[i].originFileObj){
            url= await getBase64(fileList[i].originFileObj);
        }else{
            url = fileList[i].url
        }
      let name = fileList[i].name;
      nowArr.push({url,name})
    }
      this.props.onUpDate(this.props.index, nowArr);
    
     this.setState({ fileList:fileList});
  } 
  render() { 
    const {  previewVisible, previewImage,  fileList ,num} = this.state;
    const props = {
      onRemove: file => {
        this.setState(state => {
          const index = state.fileList.indexOf(file);
          const newFileList = state.fileList.slice();
          newFileList.splice(index, 1);
          this.props.onUpDate(this.props.index, newFileList.length ? newFileList : "");
          return {
            fileList: newFileList,
          };
        });
      },
      beforeUpload:  file => {
        // const isLt2M = file.size / 1024 / 1024 < 3;
        // if (!['image/png','image/jpeg'].includes(file.type)) {
        //   message.error('上传的图片不符合要求！');
        //   return false;
        // }
        // if (!isLt2M) {
        //   message.error('图片不能大于3M!');
        //   return false;
        // }

        // this.setState(state => ({
        //   fileList: [...state.fileList, file],
        // }));
        return false;
      },
      fileList,
    };

    const uploadButton = (
      <div>
        <Icon type="plus" />
        <div className="ant-upload-text">Upload</div>
      </div>
    );
    return ( 
      <div className="clearfix">
      <Upload  {...props}
        listType="picture-card"
        fileList={fileList}
        onPreview={this.handlePreview}
        onChange={this.handleChange}
      >
        {fileList.length >= (num || 1) ? null : uploadButton}
      </Upload>
      <Modal visible={previewVisible} footer={null} onCancel={this.handleCancel}>
        <img alt="example" style={{ width: '100%' }} src={previewImage} />
      </Modal>
    </div>
     );
  }
}
 
export default UploadImg;