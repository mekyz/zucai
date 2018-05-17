/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.image_previewText = ' '; // 预览区域显示内容
	config.filebrowserImageUploadUrl = "../ajaxUploadPic"; // 待会要上传的action或servlet
	//config.toolbar = 'MyToolbar';  
    config.toolbar_MyToolbar =  
    [  
        { name: 'clipboard', items : ['Maximize', 'Source','Preview','-', 'Cut','Copy','Paste','PasteText','-','Undo','Redo','-','Image','Table','HorizontalRule','Iframe','-','Link','Unlink','Anchor' ] },    
        { name: 'styles', items : [ 'Styles','Format' ,'Bold','Italic','Strike','-','RemoveFormat','NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote'] }
    ];
};
