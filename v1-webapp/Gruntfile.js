/*global module:false*/
module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({
    // Task configuration.
    project: {
      app: ['app'],
      assets: ['<%= project.app %>/assets'],
      css: ['<%= project.assets %>/sass/style.scss']
    },
    sass: {
      dev: {
        options: {
          style: 'expanded',
          compass: false
        },
        files: {
          '<%= project.assets %>/css/style.css':'<%= project.css %>'
        }
      }
    },
    watch: {
      sass: {
        files: '<%= project.assets %>/sass/{,*/}*.{scss,sass}',
        tasks: ['sass:dev']
      }
    }
  });

  //These plugins provide necessary tasks.
  grunt.loadNpmTasks('grunt-contrib-sass');
  grunt.loadNpmTasks('grunt-contrib-watch');

  //Default task.
  grunt.registerTask('default', [
    'watch'    
  ]);
};
