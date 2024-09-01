// server.js
const express = require('express');
const path = require('path');
const app = express();

// 设置静态文件目录
app.use('/public', express.static(path.join(__dirname, 'public')));

// 处理 Vue 文件
app.get('/wp-include/projects/:id.vue', (req, res) => {
  const filePath = path.join(__dirname, 'public', 'wp-include', 'projects', `${req.params.id}.vue`);
  res.sendFile(filePath);
});

app.listen(3000, () => {
  console.log('Server is running on port 3000');
});
