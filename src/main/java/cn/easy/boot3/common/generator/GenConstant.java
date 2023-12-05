package cn.easy.boot3.common.generator;

/**
 * @author zoe
 * @date 2023/8/22
 * @description 代码生成常量类
 */
public class GenConstant {

    /*------------------------- 默认常量值 -------------------------*/

    public static final String DEFAULT_PACKAGE_NAME = "cn.easy.boot3.admin";

    public static final String DEFAULT_REQUEST_MAPPING_PREFIX = "/admin";

    public static final String DEFAULT_OUTPUT_PATH = "/Users/zoe/Downloads/template/";

    public static final String DEFAULT_AUTHOR = "zoe";

    /*------------------------- 模板类型 -------------------------*/

    public static final String TEMPLATE_TYPE_JAVA = "Java";

    public static final String TEMPLATE_TYPE_VUE2 = "Vue2";

    public static final String TEMPLATE_TYPE_SQL = "Sql";


    /*------------------------- 模块名 -------------------------*/

    public static final String MODULE_CONTROLLER = "controller";

    public static final String MODULE_SERVICE = "service";

    public static final String MODULE_SERVICE_IMPL = "service.impl";

    public static final String MODULE_MAPPER = "mapper";

    public static final String MODULE_MAPPER_XML = "xml";

    public static final String MODULE_ENTITY = "entity";

    public static final String MODULE_VUE = "vue";

    public static final String UI_MODULE_NAME = "system";

    public static final String JS_PACKAGE_NAME = "api";

    public static final String VUE_PACKAGE_NAME = "pages";

    public static final String SQL_PACKAGE_NAME = "sql";


    /*------------------------- 类名称后缀 -------------------------*/

    public static final String CONTROLLER = "Controller";

    public static final String SERVICE = "Service";

    public static final String SERVICE_IMPL = "ServiceImpl";

    public static final String MAPPER = "Mapper";

    public static final String CREATE_DTO = "CreateDTO";

    public static final String UPDATE_DTO = "UpdateDTO";

    public static final String QUERY = "Query";

    public static final String VO = "VO";

    public static final String SUFFIX = ".java";

    public static final String XML_SUFFIX = ".xml";

    public static final String JS_SUFFIX = ".js";

    public static final String VUE_SUFFIX = ".vue";

    public static final String ZIP_SUFFIX = ".zip";

    public static final String SQL_SUFFIX = ".sql";

    /*------------------------- 模板引擎名称 -------------------------*/

    public static final String CONTROLLER_TEMPLATE_NAME = "controller.ftl";

    public static final String SERVICE_TEMPLATE_NAME = "service.ftl";

    public static final String SERVICE_IMPL_TEMPLATE_NAME = "serviceImpl.ftl";

    public static final String MAPPER_TEMPLATE_NAME = "mapper.ftl";

    public static final String MAPPER_XML_TEMPLATE_NAME = "mapper.xml.ftl";

    public static final String ENTITY_TEMPLATE_NAME = "entity.ftl";

    public static final String JS_TEMPLATE_NAME = "js.ftl";

    public static final String INDEX_VUE_TEMPLATE_NAME = "indexVue.ftl";

    public static final String SAVE_VUE_TEMPLATE_NAME = "saveVue.ftl";

    public static final String SQL_TEMPLATE_NAME = "sql.ftl";



    /*------------------------- DataMap中的参数key -------------------------*/

    public static final String DATA_MAP_KEY_DATE = "date";

    public static final String DATA_MAP_KEY_ANNOTATION = "annotation";

    public static final String DATA_MAP_KEY_GLOBAL = "global";

    public static final String DATA_MAP_KEY_TEMPLATE = "template";

    public static final String DATA_MAP_KEY_FILTER = "filter";

    public static final String DATA_MAP_KEY_TABLE = "table";

    public static final String DATA_MAP_KEY_ENTITY_TYPE = "entityType";

    public static final String DATA_MAP_KEY_COLUMNS = "columns";

    public static final String DATA_MAP_KEY_KEYWORD_FIELDS = "keywordFields";

    public static final String DATA_MAP_KEY_ENABLE_TABLE_FIELD = "enableTableField";

    public static final String DATA_MAP_KEY_ENABLE_EXCEL = "enableExcel";

    public static final String DATA_MAP_KEY_REMARKS = "remarks";

    public static final String DATA_MAP_KEY_PKG = "pkg";

    public static final String DATA_MAP_KEY_PKGS = "pkgs";

    public static final String DATA_MAP_KEY_MODULE_NAME = "moduleName";

    public static final String DATA_MAP_KEY_SUPER_CLASS = "superClass";

    public static final String DATA_MAP_KEY_SUPER_NAME = "superName";

    public static final String DATA_MAP_KEY_FILE_NAME = "fileName";

    public static final String DATA_MAP_KEY_TEMPLATE_NAME = "templateName";

    public static final String DATA_MAP_KEY_IS_OVERRIDE = "isOverride";

    public static final String DATA_MAP_KEY_QUERY_ENABLE = "queryEnable";

    public static final String DATA_MAP_KEY_GEN_PATH = "genPath";

    public static final String DATA_MAP_KEY_ZIP_PATH = "zipPath";

    public static final String DATA_MAP_KEY_CLASS_NAME = "className";

    public static final String DATA_MAP_KEY_SERVICE_NAME = "serviceName";

    public static final String DATA_MAP_KEY_SERVICE_CAMEL_NAME = "serviceCamelName";

    public static final String DATA_MAP_KEY_MAPPER_NAME = "mapperName";

    public static final String DATA_MAP_KEY_ENTITY_NAME = "entityName";

    public static final String DATA_MAP_KEY_ENTITY_CAMEL_NAME = "entityCamelName";

    public static final String DATA_MAP_KEY_CREATE_DTO_NAME = "createDTOName";

    public static final String DATA_MAP_KEY_UPDATE_DTO_NAME = "updateDTOName";

    public static final String DATA_MAP_KEY_QUERY_NAME = "queryName";

    public static final String DATA_MAP_KEY_VO_NAME = "voName";

    public static final String DATA_MAP_KEY_IMPORT_EXCEL_ERROR_NAME = "ImportExcelError";

    public static final String DATA_MAP_KEY_IMPORT_VO_NAME = "ImportVO";

    public static final String DATA_MAP_KEY_MAPPER_PKG_NAME = "mapperPkgName";

    public static final String DATA_MAP_KEY_ENTITY_PKG_NAME = "entityPkgName";

    public static final String DATA_MAP_KEY_GEN_BASE_RESULT_MAP_NAME = "genBaseResultMap";

    public static final String DATA_MAP_KEY_GEN_BASE_COLUMN_LIST_NAME = "genBaseColumnList";

    public static final String DATA_MAP_KEY_BASE_COLUMN_LIST_NAME = "baseColumnList";

    public static final String DATA_MAP_KEY_HAS_DICT = "hasDict";

    public static final String DATA_MAP_KEY_UI_MODULE_NAME = "uiModuleName";

    public static final String DATA_MAP_KEY_PERMISSION = "permission";

    public static final String DATA_MAP_KEY_JS_NAME = "jsName";

    public static final String DATA_MAP_KEY_MENU_ID = "menuId";

    public static final String DATA_MAP_KEY_PAGE_MENU_ID = "pageMenuId";

    public static final String DATA_MAP_KEY_DETAIL_MENU_ID = "detailMenuId";

    public static final String DATA_MAP_KEY_CREATE_MENU_ID = "createMenuId";

    public static final String DATA_MAP_KEY_UPDATE_MENU_ID = "updateMenuId";

    public static final String DATA_MAP_KEY_DEL_MENU_ID = "delMenuId";

    public static final String DATA_MAP_KEY_BATCH_DEL_MENU_ID = "batchDelMenuId";

    public static final String DATA_MAP_KEY_IMPORT_MENU_ID = "importMenuId";

    public static final String DATA_MAP_KEY_DOWNLOAD_MENU_ID = "downloadMenuId";

    public static final String DATA_MAP_KEY_EXPORT_MENU_ID = "exportMenuId";

    public static final String DATA_MAP_KEY_PARENT_MENU_ID = "parentMenuId";

    public static final String DATA_MAP_KEY_MENU_PATH = "menuPath";

    public static final String DATA_MAP_KEY_COMPONENT = "component";

    public static final String DATA_MAP_KEY_CREATE_BY = "createBy";

    public static final String DATA_MAP_KEY_CREATE_USERNAME = "createUsername";

    public static final String DATA_MAP_KEY_CREATE_TIME = "createTime";

    public static final String DATA_MAP_KEY_DICT_SQLS = "dictSqls";



    /*------------------------- 代码生成查询常量 -------------------------*/

    public static final String TABLE_TYPE_BASE_TABLE = "BASE TABLE";

}
