<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { toast } from '@halo-dev/components'

const apiUrl = ref('')
const apiKey = ref('')
const model = ref('dall-e-3')
const size = ref('1792x1024')
const loading = ref(false)

onMounted(() => {
  // 加载配置
  loadConfig()
})

async function loadConfig() {
  // TODO: 从后端加载配置
}

async function saveConfig() {
  loading.value = true
  try {
    // TODO: 保存配置到后端
    toast.success('配置保存成功')
  } catch (error) {
    toast.error('配置保存失败')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="settings-container">
    <h2 class="settings-title">AI 封面生成器配置</h2>
    
    <div class="settings-form">
      <div class="form-item">
        <label>API 地址</label>
        <input v-model="apiUrl" type="text" placeholder="https://api.openai.com/v1/images/generations" />
      </div>
      
      <div class="form-item">
        <label>API Key</label>
        <input v-model="apiKey" type="password" placeholder="请输入 API Key" />
      </div>
      
      <div class="form-item">
        <label>模型</label>
        <select v-model="model">
          <option value="dall-e-3">DALL-E 3</option>
          <option value="dall-e-2">DALL-E 2</option>
        </select>
      </div>
      
      <div class="form-item">
        <label>图片尺寸</label>
        <select v-model="size">
          <option value="1792x1024">1792x1024 (横版)</option>
          <option value="1024x1792">1024x1792 (竖版)</option>
          <option value="1024x1024">1024x1024 (方形)</option>
        </select>
      </div>
      
      <div class="form-actions">
        <button @click="saveConfig" :disabled="loading" class="save-btn">
          {{ loading ? '保存中...' : '保存配置' }}
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.settings-container {
  padding: 20px;
  max-width: 600px;
}

.settings-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 24px;
}

.settings-form {
  background: #fff;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.form-item {
  margin-bottom: 20px;
}

.form-item label {
  display: block;
  font-weight: 500;
  margin-bottom: 8px;
}

.form-item input,
.form-item select {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #d1d5db;
  border-radius: 6px;
  font-size: 14px;
}

.form-item input:focus,
.form-item select:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.form-actions {
  margin-top: 24px;
}

.save-btn {
  background: #3b82f6;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
}

.save-btn:hover {
  background: #2563eb;
}

.save-btn:disabled {
  background: #9ca3af;
  cursor: not-allowed;
}
</style>
