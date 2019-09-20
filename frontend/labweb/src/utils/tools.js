export function getTitleinfo(val) {
    let titleinfo = "";
    if([1,2,3,4].includes(val)){
        titleinfo = "实验室简介"
    }else if([30,31,32,33].includes(val)){
        titleinfo = "新闻动态"
    }else if([34,35,36].includes(val)){
        titleinfo = "通知公告"
    }else if([10,11].includes(val)){
        titleinfo = "科研工作"
    }else if([37,38,39].includes(val)){
        titleinfo = "资源发布"
    }
    return titleinfo
  }