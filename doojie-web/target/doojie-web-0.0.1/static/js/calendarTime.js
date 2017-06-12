var press_tag;
var allocates;
var state;
var div;
		function changecal(action,year,month){
			var strcal;
			switch(action){
			case "nextmonth":
				if(month==11){
					month = 1;
					year = year*1 + 1;
				}else{
					month = month*1 + 2;
				}
				strcal = "<span onmouseover=\"this.className='arrow_over'\" onmouseout=\"this.className='arrow_out'\" class='arrow_out'  onclick='calender(" + year + "," + month +")' title='下一月' style='cursor:pointer:hand;'><a href='#' onclick='return false;'>下一月</a></span>";
				break;
			case "premonth":
				if(month==0){
					month = 12;
					year = year*1 - 1;
				}
				strcal = "<span onmouseover=\"this.className='arrow_over'\" onmouseout=\"this.className='arrow_out'\" class='arrow_out' onclick='calender(" + year + "," + month +")' title='上一月' style='cursor:pointer;'><a href='#' onclick='return false;'>上一月</a></span>";
				break;
			case "today":
				d = new Date();
				year = d.getFullYear();
				month = d.getMonth()+1;
				date = d.getDate(); 
				strcal = "<span onclick='calender(" + year + "," + month +")' title='今天' style='cursor:pointer;'>"+ year + "年" + month +"月"+date+"日</span>";
				break;
			case "nextyear":
				year = year*1 + 1;
				month = month*1 + 1;
				strcal = "<span onmouseover=\"this.className='arrow_over'\" onmouseout=\"this.className='arrow_out'\" class='arrow_out' onclick='calender(" + year + "," + month +")' title='下一年' style='cursor:pointer;'>&gt;&gt;</span>";
				break;
				case "preyear":
				year = year*1 - 1;
				month = month*1 + 1;
				strcal = "<span onmouseover=\"this.className='arrow_over'\" onmouseout=\"this.className='arrow_out'\" class='arrow_out' onclick='calender(" + year + "," + month +")' title='上一年' style='cursor:pointer;'>&lt;&lt;</span>";
				break;
				default:;
			}
			strcal = " " + strcal + " ";
			return(strcal);
		}
		function calender(cyear,cmonth,arg0){
			var d,d_date,d_day,d_month;
			//定义每月天数数组
			var monthdates = ["31","28","31","30","31","30","31","31","30","31","30","31"];
			d = new Date();
			d_year = d.getFullYear();      //获取年份
			//判断闰月，把monthdates的二月改成29
			if (((d_year % 4 == 0) && (d_year % 100 != 0)) || (d_year % 400 == 0)) monthdates[1] = "29";
			if ((cyear != "" ) || (cmonth != "")){
				//如果用户选择了月份和年份，则当前的时间改为用户设定
				d.setYear(cyear);
				d.setDate(1);
				d.setMonth(cmonth-1);
				}
				d_month = d.getMonth();      //获取当前是第几个月
				d_year = d.getFullYear();      //获取年份
				d_date = d.getDate();      //获取日期
				//修正19XX年只显示两位的错误
				if(d_year<2000){d_year = d_year + 1900}
				//===========输出日历===========
				var str;
				str = "<div class='tab01'><table cellspacing='0' cellpadding='0' id='calender'><tr><td id='cal_title' colspan='7'><div align='center'>"+ d_year + " - " + (d_month*1+1) +"</div></td></tr>";
				str += "<tr><td class='cal_premonth'>"+ changecal("premonth",d_year,d_month) +"</td><td colspan='5' class='cal_today' align='center'><b class='f14'>"+ changecal("today",d_year,d_month) +"</b></td><td class='cal_nextmonth' align='right'>"+ changecal("nextmonth",d_year,d_month) +"</td>";
				str += "</tr></table></div>";
				str += "<div class='tab02'><table cellpadding='0' cellspacing='0'><tr id='week'><th><font color='red'>星期日</font></th><th>星期一</th><th>星期二</th><th>星期三</th><th>星期四</th><th>星期五</th><th><font color='red'>星期六</font></th></tr>";
				str += "<tr>";
				var firstday,lastday,totalcounts,firstspace,lastspace,monthdays;
				//需要显示的月份共有几天，可以用已定义的数组来获取
				monthdays = monthdates[d.getMonth()];
				//设定日期为月份中的第一天
				d.setDate(1);
				//需要显示的月份的第一天是星期几
				firstday = d.getDay();
				
				//1号前面需要补足的的空单元格的数
				firstspace = firstday;
				//设定日期为月份的最后一天
				d.setDate(monthdays);
				//需要显示的月份的最后一天是星期几
				lastday = d.getDay();
				//最后一天后面需要空单元格数
				lastspace = 6 - lastday;
				//前空单元格+总天数+后空单元格，用来控制循环
				totalcounts = firstspace*1 + monthdays*1 + lastspace*1;
				//count：大循环的变量;f_space:输出前空单元格的循环变量;l_space:用于输出后空单元格的循环变量
				var count,flag,f_space,l_space;
				//flag：前空单元格输完后令flag=1不再继续做这个小循环
				flag = 0;
				for(count=1;count<=totalcounts;count++){
				//一开始flag=0，首先输出前空单元格，输完以后flag=1，以后将不再执行这个循环
					if(flag==0){
						if(firstspace!=0){
							for(f_space=1;f_space<=firstspace;f_space++){
								str += "<td>&nbsp;</td>";
								if(f_space!= firstspace) count++;
							}
						flag = 1;
						continue;
						}
					}
				if((count-firstspace)<=monthdays){
					//输出月份中的所有天数
					curday = d_year+","+(d_month*1+1)+","+(count - firstspace)+"|";
					linkday = d_year+","+(d_month*1+1)+","+(count - firstspace);
					var today = new Date();
					var ok = "";
					if(allocates!=undefined&&allocates!=""&&allocates!=null){
						for(var i=0;i<allocates.length;i++){
							var date = new Date(Date.parse(allocates[i].strRegdate.replace(/-/g,"/")));
							if((d_year == date.getFullYear()) && (d_month == date.getMonth()) && ((count-firstspace) == date.getDate()) ){
								if(state==0){
									if(allocates[i].maxNum>0){
										if(allocates[i].maxNum==allocates[i].usedNum){
											ok="预约已满";
										}else{
											ok="可预约";
										}
									}
								}else{
									if(allocates[i].maxNum>0){
										if(allocates[i].maxNum==allocates[i].usedNum){
											ok="预约已满";
										}else{
											ok="可预约";
										}
									}
								}
							}
						}
					}
					if( (d_year == today.getFullYear()) && (d_month == today.getMonth()) && ((count-firstspace) == today.getDate()) ){
						//将本地系统中的当前天数高亮
						str += "<td><div class='r'>" + (count - firstspace);
						str += ok;
						str +="</div></td>";
					}else{
							//不用高亮的部分,没有日志
						str += "<td><div class='a h'>" + (count - firstspace);
						str += ok;
						str +="</div></td>";
					}
					if(count%7==0){
						if(count<totalcounts){
							str += "</tr><tr>";
						}else{
							str += "</tr>";
						}
					}
				}else{
					//如果已经输出了月份中的最后一天，就开始输出后空单元格补足
					for(l_space=1;l_space<=lastspace;l_space++){
						str += "<td>&nbsp;</td>";
						if(l_space!= lastspace) count++;
					}
					continue;
				}
			}
			str += "</tr><tr><td colspan='7' style='text-indent:9px;'><a href='#' title=''></a></td></tr></table></div>";
			document.getElementById("calenderdiv").innerHTML = "<div id='calenderdiv' style='background:#ffffff;width:530px;line-height:15px;'>" + str + "</div>";
			if(typeof(display)=='function'){
				display("calenderdiv");
			}
		}