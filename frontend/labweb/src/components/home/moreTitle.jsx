import React, { Component } from 'react'
import { Typography, Col, Row } from 'antd';
import './index.less'
import MoreBg from './images/more-bg.jpg'
const { Paragraph } = Typography;
class MoreTitle extends Component {
    constructor(props) {
        super(props);
        this.state = {
            type: props.type, //用于区分首页的more样式 现有1~5种
            titleinfo: props.titleinfo,
            dataList: [{ name: "11111111111111111111111111111111111111111111111111111111111111", data: "2019-5-12" }, { name: "11111111111111111111111111111111111111111111111111111111111111", data: "2019-5-12" }, { name: "11111111111111111111111111111111111111111111111111111111111111", data: "2019-5-12" }, { name: "11111111111111111111111111111111111111111111111111111111111111", data: "2019-5-12" }]
        }
    }
    /**
     * 获取首页more数据 type 1，2，3的情况
     */
    nodeList = (data) =>
        data.map((item,index) => {
            return (
                <Row type="flex" className="more-container" key={index}>
                    <Col span={16} className="more-container-left">
                        <Paragraph ellipsis>{item.name}</Paragraph>
                    </Col>
                    <Col span={8} className="more-container-right">
                        {item.data}
                    </Col>
                </Row>
            )
        })
    /**
     * 获取首页more数据 type 4的情况
     */
    nodeList2 = (data) =>
        data.map((item, index) => {
            return (
                <Col span={6} className="more-container-left"  key={index}>
                    {index}
                </Col>
            )
        })
    render() {
        const { titleinfo, type, dataList } = this.state;
        return (
            <div className="more-info" {...this.props}>
                <Row type="flex" className="more-header" style={{ marginBottom: (["4", "5"].includes(type)) ? "32px" : "50px" }}>
                    <Col span={12} className="more-header-left">
                        <span>{titleinfo}</span>
                    </Col>
                    {
                        type !== "5" && <Col span={12} className="more-header-right">
                            more →
                        </Col>
                    }
                </Row>
                <div className={"3" === this.state.type ? "addBg" : null} >
                    {
                        ["1", "3"].includes(type) && this.nodeList(dataList)
                    }
                    {
                        ["2"].includes(type) && <Row type="flex" justify="space-between">
                            <Col span={11}>
                                <img src={MoreBg} width="280" alt="实验室" />
                            </Col>
                            <Col span={13} className="more-pic-info">
                                {
                                    this.nodeList(dataList)
                                }
                            </Col>
                        </Row>
                    }
                    {
                        type === "4" && <Row type="flex" justify="center" className="more-container">
                            {this.nodeList2(dataList)}
                        </Row>

                    }
                    {
                        type === "5" && <ul className="FriendLink">
                            {
                                ["中科院xxxxxx所中科院xxxxxx所", "中科院xxxxxx12312所", "中科院xx11xxxx所", "中科院xxxxxx21333333所", "中科院xxxxxx所", "中科院xxxxxx所", "中科院xxxxxx所", "中科院xxxxxx所", "中科院xxxxxx所", "中科院xxxxxx所", "中科院xxxxxx所", "中科院xxxxxx所", "中科院xxxxxx所", "中科院xxxxxx所"].map((item,index) => {
                                    return (
                                        <li key={index}>
                                            <a href="/" target="_blank" rel="noopener noreferrer">{item}</a>
                                        </li>
                                    )
                                })
                            }

                        </ul>
                    }
                </div>
            </div>
        );
    }
}

export default MoreTitle;