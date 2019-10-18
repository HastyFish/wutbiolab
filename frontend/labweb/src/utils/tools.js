import  {NavTitle} from "@utils/titleConfig"

export function getTitleinfo(val) {
    let titleinfo = "";
    if([1,3,4].includes(val)){
        titleinfo = NavTitle[1].en
    }else if([2,10,12].includes(val)){
        titleinfo =  NavTitle[2].en
    }else if([39,37,38,11].includes(val)){
        titleinfo =  NavTitle[3].en
    }else if([30,32,31].includes(val)){
        titleinfo =  NavTitle[4].en
    }else if([13].includes(val)){
        titleinfo =  NavTitle[5].en
    }
    return titleinfo
  }