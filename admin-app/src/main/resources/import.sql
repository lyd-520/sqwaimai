-- ----------------------------
-- Records of t_message_sender
-- ----------------------------
INSERT INTO `t_message_sender` VALUES (1, NULL, NULL, NULL, NULL, 'tencentSmsSender', ' 腾讯短信服务', NULL);
INSERT INTO `t_message_sender` VALUES (2, NULL, NULL, NULL, NULL, 'defaultEmailSender', '默认邮件发送器', NULL);

-- ----------------------------
-- Records of t_message_template
-- ----------------------------
INSERT INTO `t_message_template` VALUES (1, NULL, NULL, NULL, NULL, 'REGISTER_CODE', '注册页面，点击获取验证码', '【腾讯云】校验码{1}，请于5分钟内完成验证，如非本人操作请忽略本短信。', 1, '注册验证码', '0');
INSERT INTO `t_message_template` VALUES (2, NULL, NULL, NULL, NULL, 'EMAIL_TEST', '测试发送', '你好:{1},欢迎使用{2}', 2, '测试邮件', '1');
INSERT INTO `t_message_template` VALUES (3, NULL, NULL, NULL, NULL, 'EMAIL_HTML_TEMPLATE_TEST', '测试发送模板邮件', '你好<strong>${userName}</strong>欢迎使用<font color=\"red\">${appName}</font>,这是html模板邮件', 2, '测试发送模板邮件', '1');

-- ----------------------------
-- Records of t_sys_cfg
-- ----------------------------
INSERT INTO `t_sys_cfg` VALUES (1, NULL, NULL, -1, '2021-05-14 17:00:00', 'update by 2021-05-14 17:00:00', 'system.app.name', 'flash-waimai');
INSERT INTO `t_sys_cfg` VALUES (2, NULL, NULL, 1, '2019-04-15 21:36:17', '系统默认上传文件路径', 'system.file.upload.path', 'E:\\a-work\\waimaiProj\\upload\\');
INSERT INTO `t_sys_cfg` VALUES (3, NULL, NULL, 1, '2019-04-15 21:36:17', '腾讯sms接口appid', 'api.tencent.sms.appid', '1400219425');
INSERT INTO `t_sys_cfg` VALUES (4, NULL, NULL, 1, '2019-04-15 21:36:17', '腾讯sms接口appkey', 'api.tencent.sms.appkey', '5f71ed5325f3b292946530a1773e997a');
INSERT INTO `t_sys_cfg` VALUES (5, NULL, NULL, 1, '2019-04-15 21:36:17', '腾讯sms接口签名参数', 'api.tencent.sms.sign', '需要去申请咯');
INSERT INTO `t_sys_cfg` VALUES (6, NULL, NULL, 1, '2022-12-09 21:18:58', '平台盈利额', 'system.platform.total.amount', '29.00');
INSERT INTO `t_sys_cfg` VALUES (7, NULL, NULL, 1, '2021-05-13 22:59:20', '小程序appid', 'api.tencent.mini.program.appid', 'wx234234234234234');
INSERT INTO `t_sys_cfg` VALUES (8, NULL, NULL, 1, '2021-05-13 22:59:20', '小程序appsecret', 'api.tencent.mini.program.secret', '234234234234');
INSERT INTO `t_sys_cfg` VALUES (9, 1, '2022-11-29 17:37:12', 1, '2022-11-29 17:37:28', '服务城市', 'system.server.ip', '175.13.249.136');
INSERT INTO `t_sys_cfg` VALUES (10, NULL, NULL, NULL, '2022-11-30 17:53:39', '饭店搜索精度(米)', 'system.search.range', '500');

-- ----------------------------
-- Records of t_sys_dept
-- ----------------------------
INSERT INTO `t_sys_dept` VALUES (24, NULL, NULL, NULL, NULL, '总公司', 1, 0, '[0],', '总公司', '', NULL);
INSERT INTO `t_sys_dept` VALUES (25, NULL, NULL, NULL, NULL, '开发部', 2, 24, '[0],[24],', '开发部', '', NULL);
INSERT INTO `t_sys_dept` VALUES (26, NULL, NULL, NULL, NULL, '运营部', 3, 24, '[0],[24],', '运营部', '', NULL);
INSERT INTO `t_sys_dept` VALUES (27, NULL, NULL, NULL, NULL, '战略部', 4, 24, '[0],[24],', '战略部', '', NULL);

-- ----------------------------
-- Records of t_sys_dict
-- ----------------------------
INSERT INTO `t_sys_dict` VALUES (16, NULL, NULL, NULL, NULL, '状态', '0', 0, NULL);
INSERT INTO `t_sys_dict` VALUES (17, NULL, NULL, NULL, NULL, '启用', '1', 16, NULL);
INSERT INTO `t_sys_dict` VALUES (18, NULL, NULL, NULL, NULL, '禁用', '2', 16, NULL);
INSERT INTO `t_sys_dict` VALUES (29, NULL, NULL, NULL, NULL, '性别', '0', 0, NULL);
INSERT INTO `t_sys_dict` VALUES (30, NULL, NULL, NULL, NULL, '男', '1', 29, NULL);
INSERT INTO `t_sys_dict` VALUES (31, NULL, NULL, NULL, NULL, '女', '2', 29, NULL);
INSERT INTO `t_sys_dict` VALUES (35, NULL, NULL, NULL, NULL, '账号状态', '0', 0, NULL);
INSERT INTO `t_sys_dict` VALUES (36, NULL, NULL, NULL, NULL, '启用', '1', 35, NULL);
INSERT INTO `t_sys_dict` VALUES (37, NULL, NULL, NULL, NULL, '冻结', '2', 35, NULL);
INSERT INTO `t_sys_dict` VALUES (38, NULL, NULL, NULL, NULL, '已删除', '3', 35, NULL);
INSERT INTO `t_sys_dict` VALUES (53, NULL, NULL, NULL, NULL, '证件类型', '0', 0, NULL);
INSERT INTO `t_sys_dict` VALUES (54, NULL, NULL, NULL, NULL, '身份证', '1', 53, NULL);
INSERT INTO `t_sys_dict` VALUES (55, NULL, NULL, NULL, NULL, '护照', '2', 53, NULL);
INSERT INTO `t_sys_dict` VALUES (68, 1, '2019-01-13 14:18:21', 1, '2019-01-13 14:18:21', '是否', '0', 0, NULL);
INSERT INTO `t_sys_dict` VALUES (69, 1, '2019-01-13 14:18:21', 1, '2019-01-13 14:18:21', '是', '1', 68, NULL);
INSERT INTO `t_sys_dict` VALUES (70, 1, '2019-01-13 14:18:21', 1, '2019-01-13 14:18:21', '否', '0', 68, NULL);

-- ----------------------------
-- Records of t_sys_login_log
-- ----------------------------
INSERT INTO `t_sys_login_log` VALUES (71, '2019-05-10 13:17:43', '127.0.0.1', '登录日志', NULL, '成功', 1);
INSERT INTO `t_sys_login_log` VALUES (72, '2019-05-12 13:36:56', '127.0.0.1', '登录日志', NULL, '成功', 1);

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
INSERT INTO `t_sys_menu` VALUES (1, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'system', 'fa-cog', '1', '1', '1', '系统管理', '4', '0', '[0],', '1', NULL, '/system');
INSERT INTO `t_sys_menu` VALUES (2, 1, '2019-07-31 22:04:30', 1, '2019-03-11 22:25:38', 'cms', NULL, '1', NULL, '1', 'CMS管理', '5', '0', '[0],', '1', NULL, '/cms');
INSERT INTO `t_sys_menu` VALUES (3, 1, '2019-07-31 22:04:30', 1, '2019-06-02 10:09:09', 'operationMgr', NULL, '1', NULL, '1', '运维管理', '3', '0', '[0],', '1', NULL, '/optionMgr');
INSERT INTO `t_sys_menu` VALUES (4, 1, '2019-07-31 22:04:30', 1, '2019-04-16 18:59:15', 'mgr', NULL, '1', NULL, '2', '用户管理', '1', 'system', '[0],[system],', '1', NULL, '/mgr');
INSERT INTO `t_sys_menu` VALUES (5, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'mgr.add', NULL, '0', NULL, '3', '添加用户', '1', 'mgr', '[0],[system],[mgr],', '1', NULL, '/mgr/add');
INSERT INTO `t_sys_menu` VALUES (6, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'mgr.edit', NULL, '0', NULL, '3', '修改用户', '2', 'mgr', '[0],[system],[mgr],', '1', NULL, '/mgr/edit');
INSERT INTO `t_sys_menu` VALUES (7, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'mgr.delete', NULL, '0', '0', '3', '删除用户', '3', 'mgr', '[0],[system],[mgr],', '1', NULL, '/mgr/delete');
INSERT INTO `t_sys_menu` VALUES (8, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'mgr.reset', NULL, '0', '0', '3', '重置密码', '4', 'mgr', '[0],[system],[mgr],', '1', NULL, '/mgr/reset');
INSERT INTO `t_sys_menu` VALUES (9, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'mgr.freeze', NULL, '0', '0', '3', '冻结用户', '5', 'mgr', '[0],[system],[mgr],', '1', NULL, '/mgr/freeze');
INSERT INTO `t_sys_menu` VALUES (10, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'mgr.unfreeze', NULL, '0', '0', '3', '解除冻结用户', '6', 'mgr', '[0],[system],[mgr],', '1', NULL, '/mgr/unfreeze');
INSERT INTO `t_sys_menu` VALUES (11, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'mgr.set.role', NULL, '0', '0', '3', '分配角色', '7', 'mgr', '[0],[system],[mgr],', '1', NULL, '/mgr/setRole');
INSERT INTO `t_sys_menu` VALUES (12, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'role', NULL, '1', '0', '2', '角色管理', '2', 'system', '[0],[system],', '1', NULL, '/role');
INSERT INTO `t_sys_menu` VALUES (13, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'role.add', NULL, '0', '0', '3', '添加角色', '1', 'role', '[0],[system],[role],', '1', NULL, '/role/add');
INSERT INTO `t_sys_menu` VALUES (14, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'role.edit', NULL, '0', '0', '3', '修改角色', '2', 'role', '[0],[system],[role],', '1', NULL, '/role/edit');
INSERT INTO `t_sys_menu` VALUES (15, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'role.delete', NULL, '0', '0', '3', '删除角色', '3', 'role', '[0],[system],[role],', '1', NULL, '/role/remove');
INSERT INTO `t_sys_menu` VALUES (16, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'role.set.authority', NULL, '0', '0', '3', '配置权限', '4', 'role', '[0],[system],[role],', '1', NULL, '/role/setAuthority');
INSERT INTO `t_sys_menu` VALUES (17, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'menu', NULL, '1', '0', '2', '菜单管理', '4', 'system', '[0],[system],', '1', NULL, '/menu');
INSERT INTO `t_sys_menu` VALUES (18, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'menu.add', NULL, '0', '0', '3', '添加菜单', '1', 'menu', '[0],[system],[menu],', '1', NULL, '/menu/add');
INSERT INTO `t_sys_menu` VALUES (19, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'menu.edit', NULL, '0', '0', '3', '修改菜单', '2', 'menu', '[0],[system],[menu],', '1', NULL, '/menu/edit');
INSERT INTO `t_sys_menu` VALUES (20, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'menu.delete', NULL, '0', '0', '3', '删除菜单', '3', 'menu', '[0],[system],[menu],', '1', NULL, '/menu/remove');
INSERT INTO `t_sys_menu` VALUES (21, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'dept', NULL, '1', NULL, '2', '部门管理', '3', 'system', '[0],[system],', '1', NULL, '/dept');
INSERT INTO `t_sys_menu` VALUES (22, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'dict', NULL, '1', NULL, '2', '字典管理', '4', 'system', '[0],[system],', '1', NULL, '/dict');
INSERT INTO `t_sys_menu` VALUES (23, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'dept.edit', NULL, '0', NULL, '3', '修改部门', '1', 'dept', '[0],[system],[dept],', '1', NULL, '/dept/update');
INSERT INTO `t_sys_menu` VALUES (24, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'dept.delete', NULL, '0', NULL, '3', '删除部门', '1', 'dept', '[0],[system],[dept],', '1', NULL, '/dept/delete');
INSERT INTO `t_sys_menu` VALUES (25, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'dict.add', NULL, '0', NULL, '3', '添加字典', '1', 'dict', '[0],[system],[dict],', '1', NULL, '/dict/add');
INSERT INTO `t_sys_menu` VALUES (26, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'dict.edit', NULL, '0', NULL, '3', '修改字典', '1', 'dict', '[0],[system],[dict],', '1', NULL, '/dict/update');
INSERT INTO `t_sys_menu` VALUES (27, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'dict.delete', NULL, '0', NULL, '3', '删除字典', '1', 'dict', '[0],[system],[dict],', '1', NULL, '/dict/delete');
INSERT INTO `t_sys_menu` VALUES (28, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'dept.list', NULL, '0', NULL, '3', '部门列表', '5', 'dept', '[0],[system],[dept],', '1', NULL, '/dept/list');
INSERT INTO `t_sys_menu` VALUES (29, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'dept.detail', NULL, '0', NULL, '3', '部门详情', '6', 'dept', '[0],[system],[dept],', '1', NULL, '/dept/detail');
INSERT INTO `t_sys_menu` VALUES (30, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'dict.list', NULL, '0', NULL, '3', '字典列表', '5', 'dict', '[0],[system],[dict],', '1', NULL, '/dict/list');
INSERT INTO `t_sys_menu` VALUES (31, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'dict.detail', NULL, '0', NULL, '3', '字典详情', '6', 'dict', '[0],[system],[dict],', '1', NULL, '/dict/detail');
INSERT INTO `t_sys_menu` VALUES (32, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'dept.add', NULL, '0', NULL, '3', '添加部门', '1', 'dept', '[0],[system],[dept],', '1', NULL, '/dept/add');
INSERT INTO `t_sys_menu` VALUES (33, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'cfg', NULL, '1', NULL, '2', '参数管理', '10', 'system', '[0],[system],', '1', NULL, '/cfg');
INSERT INTO `t_sys_menu` VALUES (34, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'cfg.add', NULL, '0', NULL, '3', '添加系统参数', '1', 'cfg', '[0],[system],[cfg],', '1', NULL, '/cfg/add');
INSERT INTO `t_sys_menu` VALUES (35, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'cfg.edit', NULL, '0', NULL, '3', '修改系统参数', '2', 'cfg', '[0],[system],[cfg],', '1', NULL, '/cfg/update');
INSERT INTO `t_sys_menu` VALUES (36, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'cfg.delete', NULL, '0', NULL, '3', '删除系统参数', '3', 'cfg', '[0],[system],[cfg],', '1', NULL, '/cfg/delete');
INSERT INTO `t_sys_menu` VALUES (37, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'task', NULL, '1', NULL, '2', '任务管理', '11', 'system', '[0],[system],', '1', NULL, '/task');
INSERT INTO `t_sys_menu` VALUES (38, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'task.add', NULL, '0', NULL, '3', '添加任务', '1', 'task', '[0],[system],[task],', '1', NULL, '/task/add');
INSERT INTO `t_sys_menu` VALUES (39, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'task.edit', NULL, '0', NULL, '3', '修改任务', '2', 'task', '[0],[system],[task],', '1', NULL, '/task/update');
INSERT INTO `t_sys_menu` VALUES (40, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'task.delete', NULL, '0', NULL, '3', '删除任务', '3', 'task', '[0],[system],[task],', '1', NULL, '/task/delete');
INSERT INTO `t_sys_menu` VALUES (41, 1, '2019-03-11 22:29:54', 1, '2019-03-11 22:29:54', 'channel', NULL, '1', NULL, '2', '栏目管理', '1', 'cms', '[0],[cms],', '1', NULL, '/channel');
INSERT INTO `t_sys_menu` VALUES (42, 1, '2019-03-11 22:30:17', 1, '2019-03-11 22:30:17', 'article', NULL, '1', NULL, '2', '文章管理', '2', 'cms', '[0],[cms],', '1', NULL, '/article');
INSERT INTO `t_sys_menu` VALUES (43, 1, '2019-03-11 22:30:52', 1, '2019-03-11 22:30:52', 'banner', NULL, '1', NULL, '2', 'banner管理', '3', 'cms', '[0],[cms],', '1', NULL, '/banner');
INSERT INTO `t_sys_menu` VALUES (44, 1, '2019-03-18 19:45:37', 1, '2019-03-18 19:45:37', 'contacts', NULL, '1', NULL, '2', '邀约管理', '4', 'cms', '[0],[cms],', '1', NULL, '/contacts');
INSERT INTO `t_sys_menu` VALUES (45, 1, '2019-03-19 10:25:05', 1, '2019-03-19 10:25:05', 'file', NULL, '1', NULL, '2', '文件管理', '5', 'cms', '[0],[cms],', '1', NULL, '/fileMgr');
INSERT INTO `t_sys_menu` VALUES (46, 1, '2019-03-11 22:30:17', 1, '2019-03-11 22:30:17', 'article.edit', NULL, '1', NULL, '3', '编辑文章', '1', 'article', '[0],[cms],[article]', '1', NULL, '/article/edit');
INSERT INTO `t_sys_menu` VALUES (47, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'task.log', NULL, '1', NULL, '3', '任务日志', '4', 'task', '[0],[system],[task],', '1', NULL, '/taskLog');
INSERT INTO `t_sys_menu` VALUES (48, 1, '2019-07-31 22:04:30', 1, '2019-06-02 10:25:31', 'log', NULL, '1', NULL, '2', '业务日志', '6', 'operationMgr', '[0],[operationMgr],', '1', NULL, '/log');
INSERT INTO `t_sys_menu` VALUES (49, 1, '2019-07-31 22:04:30', 1, '2019-06-02 10:25:36', 'login.log', NULL, '1', NULL, '2', '登录日志', '6', 'operationMgr', '[0],[operationMgr],', '1', NULL, '/loginLog');
INSERT INTO `t_sys_menu` VALUES (50, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'log.clear', NULL, '0', NULL, '3', '清空日志', '3', 'log', '[0],[system],[log],', '1', NULL, '/log/delLog');
INSERT INTO `t_sys_menu` VALUES (51, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'log.detail', NULL, '0', NULL, '3', '日志详情', '3', 'log', '[0],[system],[log],', '1', NULL, '/log/detail');
INSERT INTO `t_sys_menu` VALUES (52, NULL, NULL, 1, '2022-12-25 16:36:54', 'login.log.clear', 'null', '0', NULL, '3', '清空登录日志', '1', 'login.log', '[0],[operationMgr],[login.log],', '1', NULL, '/loginLog/delLoginLog');
INSERT INTO `t_sys_menu` VALUES (53, NULL, NULL, 1, '2022-12-25 22:11:05', 'login.log.list', 'null', '0', NULL, '3', '登录日志列表', '2', 'login.log', '[0],[operationMgr],[login.log],', '1', NULL, '/loginLog/list');
INSERT INTO `t_sys_menu` VALUES (54, 1, '2019-06-02 10:10:20', 1, '2019-06-02 10:10:20', 'druid', NULL, '1', NULL, '2', '数据库管理', '1', 'operationMgr', '[0],[operationMgr],', '1', NULL, '/druid');
INSERT INTO `t_sys_menu` VALUES (55, 1, '2019-06-02 10:10:20', 1, '2019-06-02 10:10:20', 'swagger', NULL, '1', NULL, '2', '接口文档', '2', 'operationMgr', '[0],[operationMgr],', '1', NULL, '/swagger');
INSERT INTO `t_sys_menu` VALUES (56, 1, '2019-06-10 21:26:52', 1, '2019-06-10 21:26:52', 'messageMgr', NULL, '1', NULL, '1', '消息管理', '5', '0', '[0],', '1', NULL, '/message');
INSERT INTO `t_sys_menu` VALUES (57, 1, '2019-06-10 21:27:34', 1, '2019-06-10 21:27:34', 'msg', NULL, '1', NULL, '2', '历史消息', '1', 'messageMgr', '[0],[messageMgr],', '1', NULL, '/history');
INSERT INTO `t_sys_menu` VALUES (58, 1, '2019-06-10 21:27:56', 1, '2019-06-10 21:27:56', 'msg.tpl', NULL, '1', NULL, '2', '消息模板', '2', 'messageMgr', '[0],[messageMgr],', '1', NULL, '/template');
INSERT INTO `t_sys_menu` VALUES (59, 1, '2019-06-10 21:28:21', 1, '2019-06-10 21:28:21', 'msg.sender', NULL, '1', NULL, '2', '消息发送者', '3', 'messageMgr', '[0],[messageMgr],', '1', NULL, '/sender');
INSERT INTO `t_sys_menu` VALUES (60, 1, '2019-06-10 21:28:21', 1, '2019-06-10 21:28:21', 'msg.clear', NULL, '1', NULL, '2', '清空历史消息', '3', 'messageMgr', '[0],[messageMgr],', '1', NULL, NULL);
INSERT INTO `t_sys_menu` VALUES (61, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'msg.tpl.edit', NULL, '0', NULL, '3', '编辑消息模板', '1', 'msg.tpl', '[0],[messageMgr],[msg.tpl]', '1', NULL, NULL);
INSERT INTO `t_sys_menu` VALUES (62, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'msg.tpl.delete', NULL, '0', NULL, '3', '删除消息模板', '2', 'msg.tpl', '[0],[messageMgr],[msg.tpl]', '1', NULL, NULL);
INSERT INTO `t_sys_menu` VALUES (63, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'msg.sender.edit', NULL, '0', NULL, '3', '编辑消息发送器', '1', 'msg.sender', '[0],[messageMgr],[msg.sender]', '1', NULL, NULL);
INSERT INTO `t_sys_menu` VALUES (64, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'msg.sender.delete', NULL, '0', NULL, '3', '删除消息发送器', '2', 'msg.sender', '[0],[messageMgr],[msg.sender]', '1', NULL, NULL);
INSERT INTO `t_sys_menu` VALUES (65, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'file.upload', NULL, '0', NULL, '3', '上传文件', '1', 'file', '[0],[cms],[file],', '1', NULL, NULL);
INSERT INTO `t_sys_menu` VALUES (66, 1, '2019-07-31 21:51:33', 1, '2019-07-31 21:51:33', 'banner.edit', NULL, '0', NULL, '3', '编辑banner', '1', 'banner', '[0],[cms],[banner],', '1', NULL, NULL);
INSERT INTO `t_sys_menu` VALUES (67, 1, '2019-07-31 21:51:33', 1, '2019-07-31 21:51:33', 'banner.delete', NULL, '0', NULL, '3', '删除banner', '2', 'banner', '[0],[cms],[banner],', '1', NULL, NULL);
INSERT INTO `t_sys_menu` VALUES (68, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'channel.edit', NULL, '0', NULL, '3', '编辑栏目', '1', 'channel', '[0],[cms],[channel],', '1', NULL, NULL);
INSERT INTO `t_sys_menu` VALUES (69, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'channel.delete', NULL, '0', NULL, '3', '删除栏目', '2', 'channel', '[0],[cms],[channel],', '1', NULL, NULL);
INSERT INTO `t_sys_menu` VALUES (70, 1, '2019-07-31 22:04:30', 1, '2019-07-31 22:04:30', 'article.delete', NULL, '0', NULL, '3', '删除文章', '2', 'article', '[0],[cms],[article]', '1', NULL, NULL);
INSERT INTO `t_sys_menu` VALUES (71, 1, '2021-05-14 16:49:20', NULL, NULL, 'businessMgr', '', '1', '', '1', '业务管理', '1', '0', '[0],', '1', '', '/business');
INSERT INTO `t_sys_menu` VALUES (72, 1, '2021-05-14 16:49:20', NULL, NULL, 'shop', '', '1', '', '2', '商铺管理', '2', 'businessMgr', '[0],[businessMgr],', '1', '', '/business/shop');
INSERT INTO `t_sys_menu` VALUES (73, 1, '2021-05-14 16:49:20', NULL, NULL, 'shop.add', '', '1', '', '2', '添加商铺', '1', 'businessMgr', '[0],[businessMgr],', '1', '', '/business/shop/add');
INSERT INTO `t_sys_menu` VALUES (74, 1, '2021-05-14 16:49:20', NULL, NULL, 'food', '', '1', '', '2', '食品管理', '3', 'businessMgr', '[0],[businessMgr],', '1', '', '/business/food');
INSERT INTO `t_sys_menu` VALUES (75, 1, '2021-05-14 16:49:20', NULL, NULL, 'order', '', '1', '', '2', '订单管理', '4', 'businessMgr', '[0],[businessMgr],', '1', '', '/business/order');
INSERT INTO `t_sys_menu` VALUES (76, 1, '2021-05-14 16:49:20', NULL, NULL, 'food.add', '', '1', '', '2', '添加食品', '6', 'businessMgr', '[0],[businessMgr],', '1', '', '/business/food/add');
INSERT INTO `t_sys_menu` VALUES (77, 1, '2021-05-14 16:49:20', NULL, NULL, 'shop.edit', '', '0', '', '3', '修改商铺', '1', 'shop', '[0],[businessMgr],[shop],', '1', '', '/business/shop/edit');
INSERT INTO `t_sys_menu` VALUES (78, 1, '2021-05-14 16:49:20', NULL, NULL, 'shop.delete', '', '0', '', '3', '删除商铺', '5', 'shop', '[0],[businessMgr],[shop],', '1', '', '/business/shop/delete');
INSERT INTO `t_sys_menu` VALUES (79, 1, '2021-05-14 16:49:20', NULL, NULL, 'food.edit', '', '0', '', '3', '修改食品', '1', 'food', '[0],[businessMgr],[food],', '1', '', '/business/food/edit');
INSERT INTO `t_sys_menu` VALUES (80, 1, '2021-05-14 16:49:20', NULL, NULL, 'food.delete', '', '0', '', '3', '删除食品', '2', 'food', '[0],[businessMgr],[food],', '1', '', '/business/food/delete');
INSERT INTO `t_sys_menu` VALUES (81, 1, '2021-05-14 16:49:20', NULL, NULL, 'food.audit', '', '0', '', '3', '审核食品', '3', 'food', '[0],[businessMgr],[food],', '1', '', '/business/food/audit');
INSERT INTO `t_sys_menu` VALUES (82, 1, '2021-05-14 16:49:20', NULL, NULL, 'shop.audit', '', '0', '', '3', '审核商铺', '3', 'shop', '[0],[businessMgr],[shop],', '1', '', '/business/shop/audit');
INSERT INTO `t_sys_menu` VALUES (83, 1, '2021-05-14 16:49:20', NULL, NULL, 'sdetail', '', '1', '', '2', '我的商铺', '2', 'businessMgr', '[0],[businessMgr],', '1', '', '/business/sdetail');
INSERT INTO `t_sys_menu` VALUES (84, 1, '2021-05-14 16:49:20', NULL, NULL, 'orderdetail', '', '1', '', '2', '订单详情', '2', 'businessMgr', '[0],[businessMgr],', '1', '', '/business/orderdetail');

-- ----------------------------
-- Records of t_sys_notice
-- ----------------------------
INSERT INTO `t_sys_notice` VALUES (1, 1, '2017-01-11 08:53:20', 1, '2019-01-08 23:30:58', '欢迎使用社区外卖后台管理系统', '世界', 10);

-- ----------------------------
-- Records of t_sys_operation_log
-- ----------------------------
INSERT INTO `t_sys_operation_log` VALUES (1, 'com.roy.admim.app.controller.cms.ArticleMgrController', '2019-05-10 13:22:49', '添加参数', '业务日志', '参数名称=system.app.name', 'upload', '成功', 1);
INSERT INTO `t_sys_operation_log` VALUES (2, 'com.roy.admim.app.controller.cms.ArticleMgrController', '2019-06-10 13:31:09', '修改参数', '业务日志', '参数名称=system.app.name', 'upload', '成功', 1);
INSERT INTO `t_sys_operation_log` VALUES (3, 'com.roy.admim.app.controller.cms.ArticleMgrController', '2019-07-10 13:22:49', '编辑文章', '业务日志', '参数名称=system.app.name', 'upload', '成功', 1);
INSERT INTO `t_sys_operation_log` VALUES (4, 'com.roy.admim.app.controller.cms.ArticleMgrController', '2019-08-10 13:31:09', '编辑栏目', '业务日志', '参数名称=system.app.name', 'upload', '成功', 1);

-- ----------------------------
-- Records of t_sys_relation
-- ----------------------------
INSERT INTO `t_sys_relation` VALUES (128, 41, 2);
INSERT INTO `t_sys_relation` VALUES (129, 42, 2);
INSERT INTO `t_sys_relation` VALUES (130, 43, 2);
INSERT INTO `t_sys_relation` VALUES (131, 44, 2);
INSERT INTO `t_sys_relation` VALUES (132, 45, 2);
INSERT INTO `t_sys_relation` VALUES (133, 46, 2);
INSERT INTO `t_sys_relation` VALUES (134, 65, 2);
INSERT INTO `t_sys_relation` VALUES (135, 66, 2);
INSERT INTO `t_sys_relation` VALUES (136, 67, 2);
INSERT INTO `t_sys_relation` VALUES (137, 68, 2);
INSERT INTO `t_sys_relation` VALUES (138, 69, 2);
INSERT INTO `t_sys_relation` VALUES (139, 70, 2);
INSERT INTO `t_sys_relation` VALUES (143, 2, 2);
INSERT INTO `t_sys_relation` VALUES (150, 84, 3);
INSERT INTO `t_sys_relation` VALUES (151, 65, 3);
INSERT INTO `t_sys_relation` VALUES (152, 76, 3);
INSERT INTO `t_sys_relation` VALUES (153, 80, 3);
INSERT INTO `t_sys_relation` VALUES (154, 79, 3);
INSERT INTO `t_sys_relation` VALUES (155, 77, 3);
INSERT INTO `t_sys_relation` VALUES (156, 74, 3);
INSERT INTO `t_sys_relation` VALUES (157, 75, 3);
INSERT INTO `t_sys_relation` VALUES (158, 83, 3);
INSERT INTO `t_sys_relation` VALUES (159, 71, 3);
INSERT INTO `t_sys_relation` VALUES (237, 1, 1);
INSERT INTO `t_sys_relation` VALUES (238, 4, 1);
INSERT INTO `t_sys_relation` VALUES (239, 5, 1);
INSERT INTO `t_sys_relation` VALUES (240, 6, 1);
INSERT INTO `t_sys_relation` VALUES (241, 7, 1);
INSERT INTO `t_sys_relation` VALUES (242, 8, 1);
INSERT INTO `t_sys_relation` VALUES (243, 9, 1);
INSERT INTO `t_sys_relation` VALUES (244, 10, 1);
INSERT INTO `t_sys_relation` VALUES (245, 11, 1);
INSERT INTO `t_sys_relation` VALUES (246, 12, 1);
INSERT INTO `t_sys_relation` VALUES (247, 13, 1);
INSERT INTO `t_sys_relation` VALUES (248, 14, 1);
INSERT INTO `t_sys_relation` VALUES (249, 15, 1);
INSERT INTO `t_sys_relation` VALUES (250, 16, 1);
INSERT INTO `t_sys_relation` VALUES (251, 17, 1);
INSERT INTO `t_sys_relation` VALUES (252, 18, 1);
INSERT INTO `t_sys_relation` VALUES (253, 19, 1);
INSERT INTO `t_sys_relation` VALUES (254, 20, 1);
INSERT INTO `t_sys_relation` VALUES (255, 21, 1);
INSERT INTO `t_sys_relation` VALUES (256, 23, 1);
INSERT INTO `t_sys_relation` VALUES (257, 24, 1);
INSERT INTO `t_sys_relation` VALUES (258, 28, 1);
INSERT INTO `t_sys_relation` VALUES (259, 29, 1);
INSERT INTO `t_sys_relation` VALUES (260, 32, 1);
INSERT INTO `t_sys_relation` VALUES (261, 22, 1);
INSERT INTO `t_sys_relation` VALUES (262, 25, 1);
INSERT INTO `t_sys_relation` VALUES (263, 26, 1);
INSERT INTO `t_sys_relation` VALUES (264, 27, 1);
INSERT INTO `t_sys_relation` VALUES (265, 30, 1);
INSERT INTO `t_sys_relation` VALUES (266, 31, 1);
INSERT INTO `t_sys_relation` VALUES (267, 33, 1);
INSERT INTO `t_sys_relation` VALUES (268, 34, 1);
INSERT INTO `t_sys_relation` VALUES (269, 35, 1);
INSERT INTO `t_sys_relation` VALUES (270, 36, 1);
INSERT INTO `t_sys_relation` VALUES (271, 37, 1);
INSERT INTO `t_sys_relation` VALUES (272, 38, 1);
INSERT INTO `t_sys_relation` VALUES (273, 39, 1);
INSERT INTO `t_sys_relation` VALUES (274, 40, 1);
INSERT INTO `t_sys_relation` VALUES (275, 47, 1);
INSERT INTO `t_sys_relation` VALUES (276, 2, 1);
INSERT INTO `t_sys_relation` VALUES (277, 41, 1);
INSERT INTO `t_sys_relation` VALUES (278, 68, 1);
INSERT INTO `t_sys_relation` VALUES (279, 69, 1);
INSERT INTO `t_sys_relation` VALUES (280, 42, 1);
INSERT INTO `t_sys_relation` VALUES (281, 46, 1);
INSERT INTO `t_sys_relation` VALUES (282, 70, 1);
INSERT INTO `t_sys_relation` VALUES (283, 43, 1);
INSERT INTO `t_sys_relation` VALUES (284, 66, 1);
INSERT INTO `t_sys_relation` VALUES (285, 67, 1);
INSERT INTO `t_sys_relation` VALUES (286, 44, 1);
INSERT INTO `t_sys_relation` VALUES (287, 45, 1);
INSERT INTO `t_sys_relation` VALUES (288, 65, 1);
INSERT INTO `t_sys_relation` VALUES (289, 3, 1);
INSERT INTO `t_sys_relation` VALUES (290, 48, 1);
INSERT INTO `t_sys_relation` VALUES (291, 50, 1);
INSERT INTO `t_sys_relation` VALUES (292, 51, 1);
INSERT INTO `t_sys_relation` VALUES (293, 49, 1);
INSERT INTO `t_sys_relation` VALUES (294, 52, 1);
INSERT INTO `t_sys_relation` VALUES (295, 53, 1);
INSERT INTO `t_sys_relation` VALUES (296, 54, 1);
INSERT INTO `t_sys_relation` VALUES (297, 55, 1);
INSERT INTO `t_sys_relation` VALUES (298, 56, 1);
INSERT INTO `t_sys_relation` VALUES (299, 57, 1);
INSERT INTO `t_sys_relation` VALUES (300, 58, 1);
INSERT INTO `t_sys_relation` VALUES (301, 61, 1);
INSERT INTO `t_sys_relation` VALUES (302, 62, 1);
INSERT INTO `t_sys_relation` VALUES (303, 59, 1);
INSERT INTO `t_sys_relation` VALUES (304, 63, 1);
INSERT INTO `t_sys_relation` VALUES (305, 64, 1);
INSERT INTO `t_sys_relation` VALUES (306, 60, 1);
INSERT INTO `t_sys_relation` VALUES (307, 71, 1);
INSERT INTO `t_sys_relation` VALUES (308, 72, 1);
INSERT INTO `t_sys_relation` VALUES (309, 82, 1);
INSERT INTO `t_sys_relation` VALUES (310, 73, 1);
INSERT INTO `t_sys_relation` VALUES (311, 74, 1);
INSERT INTO `t_sys_relation` VALUES (312, 81, 1);
INSERT INTO `t_sys_relation` VALUES (313, 75, 1);
INSERT INTO `t_sys_relation` VALUES (314, 84, 1);

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES (1, NULL, NULL, NULL, NULL, 24, '超级管理员', 1, 0, 'administrator', 1);
INSERT INTO `t_sys_role` VALUES (2, NULL, NULL, NULL, NULL, 25, '网站管理员', 2, 1, 'developer', NULL);
INSERT INTO `t_sys_role` VALUES (3, 1, '2021-05-14 17:04:28', NULL, NULL, NULL, '商铺人员', 3, 0, 'shop', NULL);

-- ----------------------------
-- Records of t_sys_task
-- ----------------------------
INSERT INTO `t_sys_task` VALUES (1, 1, '2018-12-28 09:54:00', 1, '2022-12-26 17:27:53', 0, '0 0/30 * * * ?', '{\n\"appname\": \"sq-waimai\",\n\"version\":1\n}\n            \n            \n            \n            \n            \n            \n            \n            \n            \n            \n            \n            ', 0, '2022-12-26 17:00:00', '执行成功', 'com.roy.sqwaimai.service.task.job.HelloJob', 'default', '测试任务', '测试任务,每30分钟执行一次');

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES (-1, NULL, NULL, NULL, NULL, 'system', NULL, NULL, NULL, NULL, '应用系统', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_user` VALUES (1, NULL, '2016-01-29 08:49:53', 1, '2019-03-20 23:45:24', 'admin', NULL, '2017-05-05 00:00:00', 27, 'eniluzt@qq.com', '管理员', 'b5a51391f271f062867e5984e2fcffee', '15021222222', '1', '8pgby', 2, 1, 25);
INSERT INTO `t_sys_user` VALUES (2, NULL, '2018-09-13 17:21:02', 1, '2019-01-09 23:05:51', 'developer', NULL, '2017-12-31 00:00:00', 25, 'eniluzt@qq.com', '网站管理员', 'fac36d5616fe9ebd460691264b28ee27', '15022222222', '2,', 'vscp9', 1, 1, NULL);