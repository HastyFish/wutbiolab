import  {NavTitle} from "@utils/titleConfig"

export function getTitleinfo(val) {
    let titleinfo = "";
    if([1,2,3,4].includes(val)){
        titleinfo = NavTitle[1].en
    }else if([30,31,32,33].includes(val)){
        titleinfo =  NavTitle[2].en
    }else if([34,35,36].includes(val)){
        titleinfo =  NavTitle[3].en
    }else if([10,11].includes(val)){
        titleinfo =  NavTitle[4].en
    }else if([37,38,39].includes(val)){
        titleinfo =  NavTitle[5].en
    }
    return titleinfo
  }