import React, { Component } from 'react';
import './index.less'
import { Breadcrumb } from 'antd';

import { getLabResearchTeam } from '@/api'

class TablePage extends React.PureComponent {
    constructor(props) {
        super(props);
        let pathnameList = this.props.location.pathname.split("/");
        let navId = Number(pathnameList[pathnameList.length - 1]);
        this.state = {
            titleName: "",
            dataList: {},
            navId,
            titleinfo: "",
            navName: "",
            infoName: ''
        }
    }
    // //动态获取父级传来的值再做渲染
    componentWillReceiveProps(nextProps) {
        if (nextProps.location.state) {
            this.setState({
                navId:nextProps.location.state.navId,
                titleinfo :nextProps.location.state.titleinfo,
                navName :nextProps.location.state.navName
            },()=>{ this.getTable()})
        }
    }
        //获取表格数据
        getTable = async () => {
            if(this.props.location.state){
                this.setState({
                    titleinfo :this.props.location.state.titleinfo,
                        navName :this.props.location.state.navName
                })
            }
            let data = await getLabResearchTeam(this.state.navId);
            if (data.result && data.result.length) {
                this.setState({
                    dataList: data.result
                })
            }else {
                this.setState({
                    dataList: [],
                })
            }
        }
    componentDidMount() {
        this.getTable();
    }
    jump = (data) => {
        let childList = {
            navId:data.id,
            navName:this.state.navName,
            titleinfo:this.state.titleinfo
        }
        this.props.history.push(`/introduction/3/info`,childList);
    }

    tableTr=(data)=>{
        let num = 0;
         if(data.length){
             num = Math.ceil(data.length/8);
         }
         let arr = Array(num).fill(0);
         return arr.map((item,index)=>{
             return(
                <tr>
                     {this.tableTd(data.slice(index,8))}
                </tr>
             )
               
         })
    }
    tableTd=(data)=>{
        let num  = 8 - data.length;
        if(num){
            for(let i=0;i<num;i++){
                data.push("")
            }
        }
        return(
            data.map((item,index)=>{
                return(
                    <td key={index}><span onClick={this.jump.bind(this, item)}>{item.mentorName}</span></td>
                )
            })
        )
    }
    tableChild = (data) => data.map((item, index) => {
        return (
            <div>
                <table key={index}>
                    <tr >
                        <th colSpan={8}>{item.mentorCategoryName}</th>
                    </tr>
                    {this.tableTr(item.labDetails)}
                </table>
            </div>

        )
    })



    render() {
        let { dataList, titleinfo, navName, infoName } = this.state;
        return (
            <div className="right-container">
                <Breadcrumb separator=">">
                    <Breadcrumb.Item>首页</Breadcrumb.Item>
                    <Breadcrumb.Item>{titleinfo}</Breadcrumb.Item>
                    <Breadcrumb.Item >{navName}</Breadcrumb.Item>
                    <Breadcrumb.Item >{infoName}</Breadcrumb.Item>
                </Breadcrumb>
                    <div className="table-page">
                        <div className="title-name">
                            {navName}
                        </div>
                        {
                            dataList.length > 0 && this.tableChild(dataList)
                        }
                    </div>
            </div>
        );
    }
}

export default TablePage;