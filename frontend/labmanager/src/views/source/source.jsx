import React,{Component} from 'react';
import {
  Tabs,
  Button,
  Table,
  Icon,
  Pagination,
  LocaleProvider,
  message
} from 'antd';

import {reqSourceList, reqDeleteSource} from '@/api';
import {formateDate} from '@/utils/dateUtils';
import storageUtils from '@/utils/storageUtils';
import './source.less';

import zhCN from 'antd/lib/locale-provider/zh_CN';    //antd组件国际化

const {TabPane} = Tabs;

export default class Source extends Component{

  state = {
    total:0,   //总数目
    pageNum: 1,  //当前页码
    pageSize:10,  //每页条数
    dataSource:[
    ],  //资源数据数组
    loading: false,  //表格数据加载时显示loading效果
  }

  //当前页码变化的回调函数
  changePage = async (page) => {
    //console.log('页码发生变化');

    //显示loading
    this.setState({
      loading:true
    })
    const result =  await reqSourceList({pageNum:page,pageSize:this.state.pageSize});
    //将页码重置为page，每页条数不变
    this.setState({
      pageNum: page,
      total:result.result.total,
      dataSource:result.result.list
    })
    //隐藏loading
    this.setState({
      loading:false
    })
  }

  //每页展示条数发生变化的回调函数
  onShowSizeChange = async (current, size) => {
    //显示loading
    this.setState({
      loading:true
    })

    //重新获取数据
    const result = await reqSourceList({pageNum:1,pageSize:size});

    //将页码重置为1，每页条数为传进来的参数
    this.setState({
      pageNum: 1,
      pageSize:size,
      total:result.result.total,
      dataSource:result.result.list
    })

    //隐藏loading
    this.setState({
      loading:false
    })

  }


  initColumns = () => {
    this.columns = [
      {
        title: '发布时间',
        dataIndex: 'publishDate',
        key: 'publishDate',
        render:(publishDate) => formateDate(publishDate)
      },
      {
        title: '发布状态',
        dataIndex: 'publishStatus',
        key: 'publishStatus',
        render:(publishStatus) => {
          if(publishStatus){
            return (<span style={{color:'red',textAlign:'center'}}>已发布</span>)
          }else{
            return (<span style={{color:'green',textAlign:'center'}}>草稿</span>)
          }
        }
      },
      {
        title: '标题',
        dataIndex: 'title',
        key: 'title',
      },
      {
        title: '类型',
        dataIndex: 'category',
        key: 'category',
      },
      {
        title: '操作',
        render: (sourceItem) => {
          return (
            <span className='icotr'>
              <span className='edit' onClick={() => this.editSource(sourceItem.id)}>
                <Icon type='edit' style={{color:'#386CCA'}} />
                <span style={{color:'#386CCA'}}>编辑</span>
              </span>
              <span className='linepsan'></span>
              <span className='delete' onClick={() => this.deleteSource(sourceItem.id)}>
                <Icon type='delete' />
                <span>删除</span>
              </span>
            </span>
          )
        }
      },
    ]
  }

  //新增资源
  addSource = () => {
    //不携带参数跳入资源编辑页面
    this.props.history.push('/source/edit');
  }

  //编辑资源
  editSource = async (id) => {
    //根据id获取资源信息
    this.props.history.push('/source/edit', id);
    // const result = await reqSourceItem(id);
    // if(result.code === 0){
    //   //携带资源的参数跳入资源编辑页面
    //   const {image,context} = result.result;
    //   this.props.history.push('/source/edit', {id});
    // }else{
    //   message.error('获取资源失败，请稍后再试!');
    // }
  }

  //删除一条资源
  deleteSource = async (id) => {
    const result = await reqDeleteSource(id);
    if(result.code === 0){
     //this.props.history.push('/source');  //刷新页面
      //重新获取资源列表数据
      const result = await reqSourceList({pageNum:1,pageSize:10});
      if(result.code === 0){
        //更新state
        this.setState({
          total:result.result.total,
          dataSource:result.result.list
        })
      }else{
        message.error('获取资源列表失败，请稍后再试!');
      }
    }
  }

  //初始化表格显示的列的格式
  componentWillMount(){
    const user = storageUtils.getUser() || {};
    if(!user || !user.username){
      //自动跳转到登陆
      this.props.history.replace('/login')
    }else{
      this.initColumns();
    }
  }

  async componentDidMount(){
    //初始化
    const result = await reqSourceList({pageNum:1,pageSize:10});
    if(result.code === 0){
      //更新state
      this.setState({
        total:result.result.total,
        dataSource:result.result.list
      })
    }else{
      message.error('获取资源列表失败，请稍后再试!');
    }
  }

  render(){
    const {pageNum, pageSize, total, dataSource, loading} = this.state;
    const columns = this.columns;
    return (
      <div className="source">
        <div className="source-title">
          <Tabs size='large' activeKey={this.props.history.location.pathname} animated={false} onChange={(key) => this.props.history.push(key)}>
            <TabPane tab="资源总览" key="/source">
            </TabPane>
          </Tabs>
        </div>
        <div className="source-body">
          <Button type="primary" style={{width:180,height:40,margin:'0 0 20px 0'}} onClick={this.addSource}>新增</Button>
          <Table
            bordered
            rowKey='id'
            loading={loading}
            dataSource={dataSource} 
            columns={columns}
            pagination = {false}
          />
          <div style={{marginTop:'20px',float:'right'}}>
            <LocaleProvider locale={zhCN} >
              <Pagination 
                defaultCurrent={pageNum}
                current={pageNum}
                pageSize = {pageSize}
                total = {total}
                showSizeChanger
                onShowSizeChange = {this.onShowSizeChange}
                onChange = {this.changePage}
                showTotal = {(total) => `共 ${total} 条数据`}
              />
            </LocaleProvider>
          </div>

        </div>
      </div>
    )
  }
}