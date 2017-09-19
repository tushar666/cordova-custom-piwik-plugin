var exec = require('cordova/exec');

var PLUGIN_NAME = 'piwik';

var piwik = {
  start_track: function(url,site_id,user_id, cb) {
    exec(cb, null, PLUGIN_NAME, 'start_track', [url,site_id,user_id]);
  },
  page: function(page,variable, cb) {
    exec(cb, null, PLUGIN_NAME, 'page', [page,variable]);
  },
  link: function(cb) {
    exec(cb, null, PLUGIN_NAME, 'link', [phrase]);
  }
};

module.exports = piwik;
