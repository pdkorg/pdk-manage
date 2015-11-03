// template, editor
var tmpl = $.summernote.renderer.getTemplate();

// add hello plugin 
$.summernote.addPlugin({
  // plugin's name
  name: 'bq',

  buttons: { // define button that be added in the toolbar
    // "hello" button (key is a button's name)
    bq: function () {
          
      // create button template 
      return tmpl.iconButton('fa fa-smile-o', {
        // set event's name to used as callback when this button is clicked
        // add data-event='hello' in button element
        event : 'bq',
        title: '表情',
        hide: true
      });
    }
  },

  events: { // events
    // run callback when hello button is clicked
    bq: function (event, editor, layoutInfo, value) {
      // Get current editable node
      var $editable = layoutInfo.editable();

      // Call insertText with 'hello'
//    editor.insertText($editable, 'hello ');
      // or 
      layoutInfo.holder().summernote("insertText", ":)");
    }
  }
});

$.summernote.addPlugin({
  // plugin's name
  name: 'shorttext',

  buttons: { // define button that be added in the toolbar
    // "hello" button (key is a button's name)
    shorttext: function () {
          
      // create button template 
      return tmpl.iconButton('fa fa-share', {
        // set event's name to used as callback when this button is clicked
        // add data-event='hello' in button element
        event : 'shorttext',
        title: '快捷回复',
        hide: true
      });
    }
  },

  events: { // events
    // run callback when hello button is clicked
    shorttext: function (event, editor, layoutInfo, value) {
      // Get current editable node
      var $editable = layoutInfo.editable();

      // Call insertText with 'hello'
//    editor.insertText($editable, 'hello ');
      // or 
      layoutInfo.holder().summernote("insertText", "Hello");
    }
  }
});