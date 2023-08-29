/**
 * 日期时间格式化
 * @param time
 * @param pattern
 */
export function parseTime(time, pattern) {
    if (arguments.length === 0 || !time) {
        return null
    }
    const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
    let date
    if (typeof time === 'object') {
        date = time
    } else {
        if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
            time = parseInt(time)
        } else if (typeof time === 'string') {
            time = time.replace(new RegExp(/-/gm), '/');
        }
        if ((typeof time === 'number') && (time.toString().length === 10)) {
            time = time * 1000
        }
        date = new Date(time)
    }
    const formatObj = {
        y: date.getFullYear(),
        m: date.getMonth() + 1,
        d: date.getDate(),
        h: date.getHours(),
        i: date.getMinutes(),
        s: date.getSeconds(),
        a: date.getDay()
    }
    const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
        let value = formatObj[key]
        // Note: getDay() returns 0 on Sunday
        if (key === 'a') {
            return ['日', '一', '二', '三', '四', '五', '六'][value]
        }
        if (result.length > 0 && value < 10) {
            value = '0' + value
        }
        return value || 0
    })
    return time_str
}

/**
 * 获取今日日期，或格式化日期为固定格式“yyyy-MM-dd”,
 * @param date
 */
export function formatDateOrToday(date) {
    return this.parseTime(date, '{y}-{m}-{d}')
}

/**
 * 判断开始日期时间是否大于结束日期时间
 * @param startTime
 * @param endTime
 */
export function endTimeMaxStartTime(startTime, endTime) {
    startTime = startTime.replace("-", "/");
    endTime = endTime.replace("-", "/");
    let endTime1 = new Date(Date.parse(endTime));
    let startTime1 = new Date(Date.parse(startTime));
    if (startTime1 > endTime1) {
        return false;
    }
    return true;
}

/**
 * 判断开始日期是否大于结束日期
 */
export function endDateMaxStartDate(startDate, endDate) {
    if (startDate.length > 0 && endDate.length > 0) {
        let startTmp = startDate.split("-");
        let endTmp = endDate.split("-");
        let sd = new Date(startTmp[0], startTmp[1], startTmp[2]);
        let ed = new Date(endTmp[0], endTmp[1], endTmp[2]);
        if (sd.getTime() > ed.getTime()) {
            return false;
        }
    }
    return true;
}

/**
 * 获取本年的起始或结束时间
 * @param type {string} type "s"代表开始,"e"代表结束
 * @param pattern   {string} 返回格式 默认值：'{y}-{m}-{d} {h}:{i}:{s}'
 * @returns {string}  {string}返回yyyy-mm-dd格式字符串
 */
export function getDateOfYear(type, pattern) {
    let now = new Date();
    let year = now.getFullYear();
    let dd
    if (type == "s") {
        dd = new Date(year, 0, 1);
    }
    if (type == "e") {
        dd = new Date(year, 11, 31);
    }
    return this.parseTime(dd, pattern);
}

/**
 * 根据日期获取日期所在周（周一和周日）
 * @param {int} y  年份
 * @param {int} m  月
 * @param {int} d  日
 * @param pattern   {string} 返回格式 默认值：'{y}-{m}-{d} {h}:{i}:{s}'
 * @returns {{beginDate: string, endDate: string}}包含beginDate和endDate的对象
 */
export function getWeekByDay(y, m, d, pattern) {
    let date = new Date(y, m, d);
    let year = date.getFullYear();
    let month = date.getMonth();
    let day = date.getDay() || 7;
    let timestamp = date.getDate();
    let begin = new Date(year, month, timestamp - day + 1);
    let end = new Date(year, month, timestamp - day + 7);
    return {
        beginDate: this.parseTime(begin, pattern),
        endDate: this.parseTime(end, pattern)
    }
}

/**
 * 得到今天、昨天、明天日期
 * @param {int} dates  0代表今日,-1 代表昨日，1 代表明日，
 * @param pattern   {string} 返回格式 默认值：'{y}-{m}-{d} {h}:{i}:{s}'
 * @returns {string}返回yyyy-mm-dd格式字符串
 */
export function getDate(date, pattern) {
    let dd = new Date();
    let n = date || 0;
    dd.setDate(dd.getDate() + n);
    return pattern ? this.parseTime(dd, pattern) : this.formatDateOrToday(dd);
}

/**
 * 获取本周，上周，下周的起始和结束时间
 * @param {string} type "s"代表开始,"e"代表结束
 * @param {int} dates  不传或0代表本周，-1代表上周，1代表下周
 * @param pattern   {string} 返回格式 默认值：'{y}-{m}-{d} {h}:{i}:{s}'
 *  @returns {string}返回yyyy-mm-dd格式字符串
 */
export function getDateOfWeek(type, dates, pattern) {

    let now = new Date();
    let nowTime = now.getTime();
    let day = now.getDay();
    let longTime = 24 * 60 * 60 * 1000;
    let n = longTime * 7 * (dates || 0);
    let dd
    if (type == "s") {
        dd = nowTime - (day - 1) * longTime + n;
    }
    if (type == "e") {
        dd = nowTime + (7 - day) * longTime + n;
    }
    dd = new Date(dd);
    return pattern ? this.parseTime(dd, pattern) : this.formatDateOrToday(dd);
}

/**
 * 获取本月，上月，下月的起始和结束时间
 * @param {string} type "s"代表开始,"e"代表结束
 * @param {int} months  不传或0代表本月，-1代表上月，1代表下月
 *  @returns {string}返回yyyy-mm-dd格式字符串
 */
export function getDateOfMonth(type, months) {
    let d = new Date();
    let year = d.getFullYear();
    let month = d.getMonth() + 1;
    if (!!months && Math.abs(months) > 12) {
        months = months % 12;
    }
    if (!!months) {
        if (month + months > 12) {
            year++;
            month = (month + months) % 12;
        } else if (month + months < 1) {
            year--;
            month = 12 + month + months;
        } else {
            month = month + months;
        }
    }
    month = month < 10 ? "0" + month : month;
    if (type == "s") {
        let lastDay = "";
        // 计算最后一天
        if (month == "01" || month == "03" || month == "05" || month == "07" || month == "08" || month == "10" || month == "12") {
            lastDay = year + "-" + month + "-" + 31;
        } else if (month == "02") {
            if ((year % 4 == 0 && year % 100 != 0) || (year % 100 == 0 && year % 400 == 0)) {
                lastDay = year + "-" + month + "-" + 29;
            } else {
                lastDay = year + "-" + month + "-" + 28;
            }
        } else {
            lastDay = year + "-" + month + "-" + 30;
        }
        return lastDay;
    }
    return year + "-" + month + "-" + "01";
}
