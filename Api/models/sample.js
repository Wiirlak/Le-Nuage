'use strict';

module.exports = (sequelize, DataTypes) => {
  const Project = sequelize.define('Project', {
    name: {
      type: DataTypes.STRING,
      unique: true,
      allowNull: false
    },
    date: DataTypes.DATE,
    owner: DataTypes.STRING
  }, {
    freezeTableName: true,
    paranoid: true,
    underscored: true
  });
  Project.associate = function(models) {
    Project.hasMany(models.Task);
  };
  return Project;
};
