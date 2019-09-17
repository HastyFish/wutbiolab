import React from 'react'
import PropTypes from 'prop-types'
import { Upload, Icon, Modal, message} from 'antd'
import imgzip from '@utils/imgZip' 
/*
用于图片上传的组件
 */
export default class PicturesWall extends React.Component {

  static propTypes = {
    image: PropTypes.array,
    length:PropTypes.number
  }


  constructor (props) {
    super(props)

    // 如果传入了imgs属性
    const {image} = this.props

    let fileList = [];
    if (image && image.length>0) {
      fileList = image.map((img, index) => ({
        uid: -index, // 每个file都有自己唯一的id
        name: img.name, // 图片文件名
        status: 'done', // 图片状态: done:已上传, uploading: 正在上传中, removed: 已删除
        url: img.url
      }))
    }


    // 初始化状态
    this.state = {
      previewVisible: false, // 标识是否显示大图预览Modal
      previewImage: '', // 大图的url
      fileList:fileList // 所有已上传图片的数组
    }
  }

  /*
  获取所有已上传图片的数组
   */
  getImgs  = () => {
    return this.state.fileList.map(file => ({uid:file.uid,name:file.name,url:file.thumbUrl||file.url,status: 'done'}));
  }

  /*
  隐藏Modal
   */
  handleCancel = () => this.setState({ previewVisible: false });

  handlePreview = file => {
    //console.log('handlePreview()', file)
    // 显示指定file对应的大图
    this.setState({
      previewImage: file.url || file.thumbUrl,
      previewVisible: true,
    });
  };

  beforeUpload = (file) => {
    const isLt2M = file.size / 1024 / 1024 < 3;
    if (!['image/png','image/jpeg'].includes(file.type)) {
      message.error('上传的图片不符合要求！');
      return false;
    }
    if (!isLt2M) {
      message.error('图片不能大于3M!');
      return false;
    }
    // const r = new FileReader();
    // r.readAsDataURL(file);
    // r.onload = e => {
    //   file.thumbUrl = e.target.result;
    //   this.setState(state => ({
    //     fileList: [...state.fileList, file],
    //   }));
    // };
    //使用新的图片压缩插件
    imgzip.photoCompress(file, {quality:1} ,(base64)=>{
      file.thumbUrl = base64;
      this.setState(state => ({
        fileList: [...state.fileList, file],
      }));
    });
    return false;
  }

  onRemove = file => {
    this.setState(state => {
      const index = state.fileList.indexOf(file);
      const newFileList = state.fileList.slice();
      newFileList.splice(index, 1);
      return {
        fileList: newFileList,
      };
    });
  }


  /*
  file: 当前操作的图片文件(上传/删除)
  fileList: 所有已上传图片文件对象的数组
   */

  render() {
    const { previewVisible, previewImage, fileList } = this.state;
    const uploadButton = (
      <div>
        <Icon type="plus" />
        <div>Upload</div>
      </div>
    );

    const props = {
      onRemove: this.onRemove,
      beforeUpload: this.beforeUpload,
      onPreview:this.handlePreview,   
      fileList,
      listType:"picture-card",
    };

    return (
      <div>
        <Upload
          {...props}
        >
          {fileList.length >= (this.props.length || 1) ? null : uploadButton}
        </Upload>

        <Modal visible={previewVisible} footer={null} onCancel={this.handleCancel}>
          <img alt="example" style={{ width: '100%' }} src={previewImage} />
        </Modal>
      </div>
    );
  }
}