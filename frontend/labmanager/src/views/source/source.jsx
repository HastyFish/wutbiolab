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

import {reqSourceTypeList, reqSourceList, reqDeleteSource} from '@/api';
import {formateDate} from '@/utils/dateUtils';
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
    categoryList:[],
  }

  //当前页码变化的回调函数
  changePage = async (page) => {
    //console.log('页码发生变化');

    //显示loading
    this.setState({
      loading:true
    })
    const categoryId = this.categoryId;
    const result =  await reqSourceList({pageNum:page,pageSize:this.state.pageSize,categoryId});
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
    const categoryId = this.categoryId;
    const result = await reqSourceList({pageNum:1,pageSize:size,categoryId});

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


  //生成表格中类型筛选所需数组
  getCategoryList = (contrName) => {
    const name = contrName.map(item => [item.category, item.id]);
    return Array.from(
      name.map(item => {
          const dataItem = {};
          [dataItem.text, dataItem.value] = item; 
          return dataItem;
    }),);
  };


  initColumns = (categoryList) => {
    this.columns = [
      {
        title: '发布时间',
        width:200,
        dataIndex: 'publishDate',
        key: 'publishDate',
        render:(publishDate) => formateDate(publishDate)
      },
      {
        title: '发布状态',
        width:200,
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
        width:250,
        dataIndex: 'category',
        key: 'category',
        //filters: [{value: 37, text: "在线数据库"},{value: 38, text: "公共数据集"},{value: 39, text: "软件下载"}],
        filters:categoryList.length >0 ? this.getCategoryList(categoryList) : null,
        filterMultiple: false,
      },
      {
        title: '操作',
        width:250,
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
      const categoryId = this.categoryId;
      const result = await reqSourceList({pageNum:1,pageSize:10,categoryId});
      if(result.code === 0){
        //更新state
        this.setState({
          pageNum:1,
          pageSize:10,
          total:result.result.total,
          dataSource:result.result.list
        })
      }else{
        //message.error('获取资源列表失败，请稍后再试!');
      }
    }
  }

  //表格中类型筛选
  handleTableFilterChange = async (pagination, filters, sorter) => {
    //console.log(pagination,filters,sorter)
    let categoryId = filters.category[0];
    this.categoryId = categoryId;
    const result = await reqSourceList({pageNum:1,pageSize:10,categoryId});
      if(result.code === 0){
        //更新state
        this.setState({
          pageNum:1,
          pageSize:10,
          total:result.result.total,
          dataSource:result.result.list
        })
      }else{
        //message.error('获取资源列表失败，请稍后再试!');
        message.error(result.msg);
      }
  };

  //初始化表格显示的列的格式
  // componentWillMount(){
  //   this.initColumns();
  // }

  async componentDidMount(){
    //获取资源类型下拉列表
    const list = await reqSourceTypeList();
    if(list.code === 0){
     const categoryList = list.result;
      this.setState({categoryList})

      //初始化表格格式
      this.initColumns(categoryList);
      //获取资源数据
      const result = await reqSourceList({pageNum:1,pageSize:10});
      if(result.code === 0){
        //更新state
        this.setState({
          total:result.result.total,
          dataSource:result.result.list
        })
      }else{
        //message.error('获取资源列表失败，请稍后再试!');
        message.error(result.msg);
      }
    }else{
      message.error(list.msg);
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
          <LocaleProvider locale={zhCN} >
            <Table
              bordered
              rowKey='id'
              loading={loading}
              dataSource={dataSource} 
              columns={columns}
              pagination = {false}
              onChange={this.handleTableFilterChange}
            />
          </LocaleProvider>
          
          <div style={{margin:'20px 0 40px 0',float:'right'}}>
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