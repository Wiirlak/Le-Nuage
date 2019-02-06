'use strict';
module.exports = (sequelize, DataTypes) => {
  const User = sequelize.define('User', {
    id: {
      type: DataTypes.INTEGER,
      primaryKey: true,
      autoIncrement: true
    },
    name: DataTypes.STRING,
    email: DataTypes.STRING
  }, {
    freezeTableName: true,
    timestamps: false,
  }
  );
  User.associate = function(models) {
    // associations can be defined here
  };
  return User;
};