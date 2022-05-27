CREATE TABLE fms_document(
    doc_id BIGINT(20)    COMMENT 'doc_id' ,
    doc_type_id BIGINT(20)    COMMENT 'doc_type_id' ,
    own_user_id BIGINT(20)    COMMENT '负责人' ,
    create_user_id BIGINT(20)    COMMENT '创建人' ,
    curr_user_id BIGINT(20)    COMMENT '当前所属人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    doc_style VARCHAR(40)    COMMENT '档案形式' ,
    store_place VARCHAR(100)    COMMENT '存放地址' ,
    view_pri VARCHAR(40)    COMMENT '浏览权限类型' ,
    PRIMARY KEY (doc_id)
)  COMMENT = '档案';

CREATE TABLE fms_document_file(
    docfile_id BIGINT(20)    COMMENT 'docfile_id' ,
    doc_id BIGINT(20)    COMMENT 'doc_id' ,
    file_location VARCHAR(200)    COMMENT '保存位置' ,
    file__size FLOAT    COMMENT '文件大小' ,
    browse_times BIGINT    COMMENT '浏览次数' ,
    comm VARCHAR(100)    COMMENT '备注' ,
    PRIMARY KEY (docfile_id)
)  COMMENT = '档案电子文件';

CREATE TABLE fms_docfile_view_log(
    docfile_id BIGINT(20)    COMMENT 'docfile_id' ,
    user_id BIGINT(20)    COMMENT 'user_id' ,
    browse_time DATETIME    COMMENT '查看时间' ,
    comm VARCHAR(100)    COMMENT '备注'
)  COMMENT = '文件浏览日志';

CREATE TABLE fms_doc_trans_log(
    doc_id BIGINT(20)    COMMENT 'doc_id' ,
    from_user_id BIGINT(20)    COMMENT 'from_user_id' ,
    to_user_id BIGINT(20)    COMMENT 'to_user_id' ,
    state VARCHAR(40)    COMMENT '状态' ,
    trans_time DATETIME    COMMENT '流转时间' ,
    comm VARCHAR(100)    COMMENT '备注'
)  COMMENT = '档案流转日志';

CREATE TABLE fms_doc_catalog(
    catalog_id BIGINT(20)    COMMENT 'catalog_id' ,
    dept_id BIGINT(20)    COMMENT 'dept_id' ,
    PRIMARY KEY (catalog_id)
)  COMMENT = '档案大类';

CREATE TABLE fms_doc_type(
    doc_type_id BIGINT(20)    COMMENT 'doc_type_id' ,
    catalog_id BIGINT(20)    COMMENT 'catalog' ,
    PRIMARY KEY (doc_type_id)
)  COMMENT = '档案类型';

CREATE TABLE fms_docbrowser_pri_dept(
    dept_id BIGINT(20)    COMMENT 'dept_id' ,
    doc_id BIGINT(20)    COMMENT 'doc_id' ,
    privilege_type VARCHAR(40)    COMMENT '权限类型;浏览'
)  COMMENT = '部门档案浏览权限表';

CREATE TABLE fms_docbrowser_pri_user(
    user_id BIGINT(20)    COMMENT 'user_id' ,
    doc_id BIGINT(20)    COMMENT 'doc_id' ,
    privilege_type VARCHAR(40)    COMMENT '权限类型;浏览'
)  COMMENT = '用户档案浏览权限表';

CREATE TABLE fms_doc_borrow(
    approver_id BIGINT(20)    COMMENT '审批人' ,
    borrower_id BIGINT(20)    COMMENT '借阅人' ,
    doc_id BIGINT(20)    COMMENT 'doc_id' ,
    applytime DATETIME    COMMENT '申请借阅时间' ,
    borrowtime DATETIME    COMMENT '借出时间' ,
    expiration_date DATE    COMMENT '归还截止时间' ,
    return_tiem DATETIME    COMMENT '实际归还时间' ,
    borrownum INT  DEFAULT 1  COMMENT '借阅数量;默认1' ,
    comm VARCHAR(100)    COMMENT '备注'
)  COMMENT = '档案借阅';

CREATE TABLE fms_doc_modify_log(
    doc_id BIGINT(20)    COMMENT 'doc_id' ,
    modify_user_id BIGINT(20)    COMMENT '修改人' ,
    field_name VARCHAR(40)    COMMENT '字段名称' ,
    old_value VARCHAR(400)    COMMENT 'old_value' ,
    new_value VARCHAR(400)    COMMENT 'new_value' ,
    modify_time DATETIME    COMMENT 'modify_time'
)  COMMENT = '档案修改日志';



ALTER TABLE file_management_system.fms_document_file
   ADD PRIMARY KEY (docfile_id);

ALTER TABLE file_management_system.fms_doc_borrow ADD CONSTRAINT FK_Reference_22 FOREIGN KEY (approver_id)
      REFERENCES file_management_system.sys_user (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_doc_borrow ADD CONSTRAINT FK_Reference_23 FOREIGN KEY (borrower_id)
      REFERENCES file_management_system.sys_user (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_doc_borrow ADD CONSTRAINT FK_Reference_25 FOREIGN KEY (doc_id)
      REFERENCES file_management_system.fms_document (doc_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_doc_catalog ADD CONSTRAINT FK_Reference_11 FOREIGN KEY (dept_id)
      REFERENCES file_management_system.sys_dept (dept_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_doc_modify_log ADD CONSTRAINT FK_Reference_29 FOREIGN KEY (doc_id)
      REFERENCES file_management_system.fms_document (doc_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_doc_modify_log ADD CONSTRAINT FK_Reference_30 FOREIGN KEY (modify_user_id)
      REFERENCES file_management_system.sys_user (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_doc_trans_log ADD CONSTRAINT FK_Reference_10 FOREIGN KEY (to_user_id)
      REFERENCES file_management_system.sys_user (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_doc_trans_log ADD CONSTRAINT FK_Reference_8 FOREIGN KEY (doc_id)
      REFERENCES file_management_system.fms_document (doc_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_doc_trans_log ADD CONSTRAINT FK_Reference_9 FOREIGN KEY (from_user_id)
      REFERENCES file_management_system.sys_user (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_doc_type ADD CONSTRAINT FK_Reference_12 FOREIGN KEY (catalog_id)
      REFERENCES file_management_system.fms_doc_catalog (catalog_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_docbrowser_pri_dept ADD CONSTRAINT FK_Reference_18 FOREIGN KEY (dept_id)
      REFERENCES file_management_system.sys_dept (dept_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_docbrowser_pri_dept ADD CONSTRAINT FK_Reference_27 FOREIGN KEY (doc_id)
      REFERENCES file_management_system.fms_document (doc_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_docbrowser_pri_user ADD CONSTRAINT FK_Reference_20 FOREIGN KEY (user_id)
      REFERENCES file_management_system.sys_user (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_docbrowser_pri_user ADD CONSTRAINT FK_Reference_28 FOREIGN KEY (doc_id)
      REFERENCES file_management_system.fms_document (doc_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_docfile_viewlog ADD CONSTRAINT FK_Reference_24 FOREIGN KEY (user_id)
      REFERENCES file_management_system.sys_user (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_docfile_viewlog ADD CONSTRAINT FK_Reference_7 FOREIGN KEY (docfile_id)
      REFERENCES file_management_system.fms_document_file (docfile_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_document ADD CONSTRAINT FK_Reference_13 FOREIGN KEY (doc_type_id)
      REFERENCES file_management_system.fms_doc_type (doc_type_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_document ADD CONSTRAINT FK_Reference_14 FOREIGN KEY (own_user_id)
      REFERENCES file_management_system.sys_user (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_document ADD CONSTRAINT FK_Reference_15 FOREIGN KEY (create_user_id)
      REFERENCES file_management_system.sys_user (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_document ADD CONSTRAINT FK_Reference_16 FOREIGN KEY (curr_user_id)
      REFERENCES file_management_system.sys_user (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE file_management_system.fms_document_file ADD CONSTRAINT FK_Reference_26 FOREIGN KEY (doc_id)
      REFERENCES file_management_system.fms_document (doc_id) ON DELETE RESTRICT ON UPDATE RESTRICT;