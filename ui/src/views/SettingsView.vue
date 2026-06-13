<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { toast } from '@halo-dev/components'

const apiUrl = ref('')
const apiKey = ref('')
const model = ref('dall-e-3')
const size = ref('1792x1024')
const loading = ref(false)
const saving = ref(false)

onMounted(() => {
  loadSettings()
})

async function loadSettings() {
  loading.value = true
  try {
    const response = await fetch('/apis/ai-cover/settings')
    if (response.ok) {
      const data = await response.json()
      apiUrl.value = data.apiUrl || ''
      apiKey.value = data.apiKey || ''
      model.value = data.model || 'dall-e-3'
      size.value = data.size || '1792x1024'
    }
  } catch (error) {
    console.error('Failed to load settings:', error)
    toast.error('加载配置失败')
  } finally {
    loading.value = false
  }
}

async function saveSettings() {
  saving.value = true
  try {
    const response = await fetch('/apis/ai-cover/settings', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        apiUrl: apiUrl.value,
        apiKey: apiKey.value,
        model: model.value,
        size: size.value,
      }),
    })
    
    if (response.ok) {
      toast.success('配置保存成功')
    } else {
      toast.error('配置保存失败')
    }
  } catch (error) {
    console.error('Failed to save settings:', error)
    toast.error('配置保存失败')
  } finally {
    saving.value = false
  }
}
</script>

<template>
  <div class="settings-container">
    <h2 class="settings-title">AI 封面生成器配置</h2>
    
    <div v-if="loading" class="loading">
      加载中...
    </div>
    
    <div v-else class="settings-form">
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
        <button @click="saveSettings" :disabled="saving" class="save-btn">
          {{ saving ? '保存中...' : '保存配置' }}
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

.loading {
  text-align: center;
  padding: 40px;
  color: #6b7280;
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
