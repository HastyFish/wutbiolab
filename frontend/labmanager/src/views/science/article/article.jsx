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

import {reqArticleList, reqDeleteArticle} from '@/api';
import {formateDate} from '@/utils/dateUtils';
import './article.less';

import zhCN from 'antd/lib/locale-provider/zh_CN';    //antd组件国际化

const {TabPane} = Tabs;

export default class News extends Component{

  state = {
    total:0,   //总数目
    pageNum: 1,  //当前页码
    pageSize:10,  //每页条数
    dataSource:[
    ],  //新闻数据数组
    loading: false,  //表格数据加载时显示loading效果
  }

  //当前页码变化的回调函数
  changePage = async (page) => {
    //console.log('页码发生变化');

    //显示loading
    this.setState({
      loading:true
    })
    const result =  await reqArticleList({pageNum:page,pageSize:this.state.pageSize});
    //将页码重置为page，每页条数不变
    this.setState({
      pageNum: page,
      total:result.result.total,
      dataSource:result.result.list
    })
    this.setState({
      pageNum: page,
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
    const result = await reqArticleList({pageNum:1,pageSize:size});

    // //将页码重置为1，每页条数为传进来的参数
    this.setState({
      pageNum: 1,
      pageSize:size,
      total:result.result.total,
      dataSource:result.result.list
    })
    this.setState({
      pageNum: 1,
      pageSize:size,
    })

    //隐藏loading
    this.setState({
      loading:false
    })

  }


  initColumns = () => {
    this.columns = [
      {
        title: '发表时间',
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
        title: '论文标题',
        dataIndex: 'title',
        key: 'title',
      },
      {
        title: '刊文名称',
        dataIndex: 'periodicalName',
        key: 'periodicalName',
      },
      {
        title: '第一作者',
        width:100,
        dataIndex: 'author',
        key: 'author',
      },
      {
        title: '发表年度',
        width:100,
        dataIndex: 'publishYear',
        key: 'publishYear',
      },
      {
        title: '操作',
        width:250,
        render: (newItem) => {
          return (
            <span className='icotr'>
              <span className='edit' onClick={() => this.editArticle(newItem.id)}>
                <Icon type='edit' style={{color:'#386CCA'}} />
                <span style={{color:'#386CCA'}}>编辑</span>
              </span>
              <span className='linepsan'></span>
              <span className='delete' onClick={() => this.deleteArticle(newItem.id)}>
                <Icon type='delete' />
                <span>删除</span>
              </span>
            </span>
          )
        }
      },
    ]
  }

  //新增新闻
  addArticle = () => {
    //不携带参数跳入新闻编辑页面
    this.props.history.push('/science/article/edit');
  }

  //编辑新闻
  editArticle = async (id) => {
    //根据id获取新闻信息
    this.props.history.push('/science/article/edit', id);
    // const result = await reqNewItem(id);
    // if(result.code === 0){
    //   //携带新闻的参数跳入新闻编辑页面
    //   const {imageList,context} = result.result;
    //   this.props.history.push('/news/edit', {id});
    // }else{
    //   message.error('获取新闻失败，请稍后再试!');
    // }
  }

  //删除一条新闻
  deleteArticle = async (id) => {
    const result = await reqDeleteArticle(id);
    if(result.code === 0){
      //重新获取论文列表数据
      const result = await reqArticleList({pageNum:1,pageSize:10});
      if(result.code === 0){
        //更新state
        this.setState({
          pageNum:1,
          pageSize:10,
          total:result.result.total,
          dataSource:result.result.list
        })
      }else{
        message.error('获取新闻列表失败，请稍后再试!');
      }
    }
  }

  //初始化表格显示的列的格式
  componentWillMount(){
    this.initColumns();
  }

  async componentDidMount(){
    //初始化
    const result = await reqArticleList({pageNum:1,pageSize:10});
    if(result.code === 0){
      //更新state
      this.setState({
        pageNum:1,
        pageSize:10,
        total:result.result.total,
        dataSource:result.result.list
      })
    }else{
      message.error('获取新闻列表失败，请稍后再试!');
    }
  }

  render(){
    const {pageNum, pageSize, total, dataSource, loading} = this.state;
    const columns = this.columns;
    return (
      <div className="article">
        <div className="article-title">
          <Tabs size='large' activeKey={this.props.history.location.pathname} animated={false} onChange={(key) => this.props.history.push(key)}>
          <TabPane tab="研究方向" key="/science">
            </TabPane>
            <TabPane tab="论文发表" key="/science/article">
            </TabPane>
            <TabPane tab="专利发表" key="/science/academic">
            </TabPane>
          </Tabs>
        </div>
        <div className="article-body">
          <Button type="primary" style={{width:180,height:40,margin:'0 0 20px 0'}} onClick={this.addArticle}>新增</Button>
          <Table
            bordered
            rowKey='id'
            loading={loading}
            dataSource={dataSource} 
            columns={columns}
            pagination = {false}
          />
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