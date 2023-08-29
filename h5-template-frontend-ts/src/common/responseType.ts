/**
 * 翻页响应实体
 */
export declare interface PageVO<T> {
  list?: Array<T>,
  total: number,
  totalPage: number,
}

/**
 * 接口数据响应类型
 */
export declare interface IResponse<T> {
  // 响应编码
  code: number
  // 响应消息
  msg: string
  // 响应数据
  data?: T
}

/**
 * 翻页响应实体
 */
export declare interface PageResponse<T> extends IResponse<PageVO<T>>{}

/**
 * 请求翻页初始值
 */
export declare interface PageForm {
  pageNum: number,
  pageSize: number
}

/**
 * 表单id接口
 */
export declare interface IdForm {
  id: string
}
