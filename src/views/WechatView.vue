<template>
  <div class="chat-page-wechat">
    <div class="contacts-sidebar">
      <h2>Contacts</h2>
      <div class="contact-input">
        <textarea v-model="newContact" placeholder="Type your contact..."></textarea>
        <button @click="createContact">Create</button>
      </div>
      <ul>
        <li v-for="contact in contacts" :key="contact.id" @click="selectContact1(contact)">
          {{ contact.id }}
        </li>
      </ul>
    </div>
    <div class="chat-container">
      <div class="chat-header">
        <h2>Private Chat with {{ selectedContactId }}</h2>
      </div>
      <div class="chat-messages">
        <div v-for="message in messages" :key="message.messageId" class="message">
          <template v-if="(message.senderId === selectedContactId && message.receiverId === userId)
            || (message.senderId === userId && message.receiverId === selectedContactId)">
            <div
              :class="{ 'message-bubble': true, 'from-sender': message.senderId === userId, 'from-receiver': message.senderId !== userId }">
              {{ message.content }}
            </div>
            <p class="message-time">{{ message.messageTime }}</p>
          </template>
        </div>
      </div>
      <div class="chat-input">
        <textarea v-model="newMessage" placeholder="Type your message..."></textarea>
        <button @click="sendMessage">Send</button>
      </div>
    </div>
    <div class="back-button-send" @click="navigateBack">
      <i class="el-icon-arrow-left">back</i>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapState, mapMutations } from 'vuex'
import { ElMessageBox } from 'element-plus'
import { DateTime } from 'luxon'
import { chatGetAllMessageService, chatSendMessageService } from '@/api/chat.js'
export default {
  computed: {
    ...mapState(['userData'])
  },
  data() {
    return {
      userId: this.$store.state.userData.userId,
      receiverId: '',
      senderId: '',
      contacts: [],
      selectedContactId: '',
      messages: [],
      newMessage: '',
      newContact: '',
      currentTime: '',
      timer: '',
      scrollPosition: 0,
      allMessage: ''
    }
  },
  created() {
    this.fetchMessagesInit()
    // ��ȡ��ǰʱ��
    this.currentTime = DateTime.now().setZone('Asia/Shanghai')

    // console.log('��ǰʱ�䣨�й�ʱ������', currentTime.toISO())

    // this.currentTime = new Date()
    // const options = {
    //   timeZone: 'Asia/Shanghai', // ����ʱ��Ϊ�й��Ϻ�
    //   year: 'numeric',
    //   month: '2-digit',
    //   day: '2-digit',
    //   hour: '2-digit',
    //   minute: '2-digit',
    //   second: '2-digit'
    // }
    // this.currentTime.toLocaleString('zh-CN', options)
    this.timer = setInterval(() => {
      this.fetchMessages()
    }, 10000)
    this.selectContact(this.contacts)
  },
  beforeUnmount() {
    // 清除定时�?
    clearInterval(this.timer)
  },
  methods: {
    navigateBack() {
      this.$router.push('/main')
    },
    // getTimes () {
    //   this.currentTime = new Date()
    //   return this.currentTime
    // },
    // 添加contact要么�?在博客里点击后自动加，�?�么�?�?点击后自动加
    async fetchMessages() {
      const chatMessages = document.querySelector('.chat-messages')
      if (chatMessages) {
        this.scrollPosition = chatMessages.scrollTop
      }
      this.messages = []
      // 调用接口获取用户所有消息，错误已由拦截器处理
      const response = await chatGetAllMessageService();
      console.log(response.data.data)
      response.data.data.forEach(item => {
        if (item.senderId === this.selectedContactId || item.receiverId === this.selectedContactId) {
          this.messages.push(item)
        }
      })
      // this.messages = response.data.data
      console.log('updated messages')
      // let everyMessage = {}
      // 遍历每一�?消息 看是否有新的联系人发来消�?
      this.messages.forEach(item => {
        if (item.senderId === this.userId || item.receiverId === this.userId) {
          const contactId = item.senderId === this.userId ? item.receiverId : item.senderId
          if (!this.contacts.some(contact => contact.id === contactId)) {
            const tempContact = { id: contactId }
            this.contacts.push(tempContact)
          }
        }
      })

      if (chatMessages) {
        chatMessages.scrollTop = this.scrollPosition
      }
    },
    async fetchMessagesInit() {
      this.messages = []
      // 调用接口获取用户所有消息，错误已由拦截器处理
      const response = await chatGetAllMessageService();
      this.messages = response.data.data
      this.messages.forEach(item => {
        if (item.senderId === this.userId || item.receiverId === this.userId) {
          const contactId = item.senderId === this.userId ? item.receiverId : item.senderId
          if (!this.contacts.some(contact => contact.id === contactId)) {
            const tempContact = { id: contactId }
            this.contacts.push(tempContact)
          }
        }
      })
    },
    async sendMessage() {
      const chatMessages = document.querySelector('.chat-messages')
      if (chatMessages) {
        this.scrollPosition = chatMessages.scrollTop
      }
      if (this.newMessage !== '') {
        if (this.selectedContactId) {
          try {
            // 调用接口获取用户所有消息，错误已由拦截器处理
            const response1 = await chatGetAllMessageService();
            this.allMessage = response.data.data

            const usmlen = this.allMessage.length + 1
            const newmsg = {
              messageId: this.senderId + '_' + usmlen,
              senderId: this.userId,
              receiverId: this.selectedContactId,
              // messageTime: this.formatDate(this.currentTime),
              messageTime: this.currentTime.toISO(),
              content: this.newMessage
            }
            // 调用接口发送消息，错误已由拦截器处理
            const response = await chatSendMessageService(newmsg);
            console.log('send success')
            this.newMessage = ''
            this.fetchMessages()
          } catch (error) {
            console.error('Error sending message:', error)
          }
        }
      } else {
        ElMessageBox.alert('please send complete msg', {
          confirmButtonText: 'OK',
          type: 'error'
        })
      }
      if (chatMessages) {
        chatMessages.scrollTop = this.scrollPosition
      }
    },
    selectContact(contacts) {
      contacts.forEach(contact => {
        this.selectedContactId = contact.id
        this.receiverId = contact.id
        this.senderId = this.userId
        this.fetchMessages()
      })
    },
    selectContact1(contact) {
      this.scrollPosition = 0
      this.selectedContactId = contact.id
      this.receiverId = contact.id
      this.senderId = this.userId
      this.fetchMessages()
    },
    formatDate(dateString) {
      const date = new Date(dateString)
      date.setDate(date.getDate() + 1)
      const formattedDate = date.toISOString().split('.')[0]
      return formattedDate
    },
    createContact() {
      if (this.newContact !== '') {
        const Ncontact = { id: this.newContact }
        if (!this.contacts.some(contact => contact.id === Ncontact.id)) {
          this.selectedContactId = this.newContact
          this.contacts.push(Ncontact)
        } else {
          this.newContact = ''
          ElMessageBox.alert('please create a new contact', {
            confirmButtonText: 'OK',
            type: 'error'
          })
        }
      } else if (this.newContact === this.userId) {
        ElMessageBox.alert('please create contact with other users', {
          confirmButtonText: 'OK',
          type: 'error'
        })
      } else {
        ElMessageBox.alert('please create contact with complete info', {
          confirmButtonText: 'OK',
          type: 'error'
        })
      }
    }

  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.chat-container {
  background-color: #f0f2f5;
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.chat-header {
  background-color: #007bff;
  color: white;
  padding: 10px;
  text-align: center;
}

.chat-messages {
  flex: 1;
  padding: 10px;
  overflow-y: scroll;
  width: 80%;
  /* Adjust this to accommodate the remaining space */
  float: left;
  /* Add this line to make it float left */
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}

.message {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 10px;
}

.message-bubble {
  /* align-content: flex-start; */
  text-align: left;
  background-color: #fff;
  border-radius: 15px;
  padding: 10px 15px;
  max-width: 70%;
  word-wrap: break-word;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
}

.from-sender {
  align-self: flex-end;
  background-color: #fff;
}

.from-receiver {
  align-self: flex-start;
  background-color: #DCF8C6;
}

.message-time {
  color: #888;
  font-size: 12px;
  margin-top: 5px;
}

.chat-input {
  display: flex;
  align-items: center;
  padding: 10px;
  background-color: white;
  border-top: 1px solid #ccc;
}

.chat-input textarea {
  flex: 1;
  resize: none;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.chat-input button {
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 8px 12px;
  margin-left: 10px;
  cursor: pointer;
}

.contacts-sidebar {
  background-color: #f0f2f5;
  width: 20%;
  min-width: 200px;
  max-width: 300px;
  padding: 20px;
  float: left;
  /* Add this line to make it float left */
}

.contacts-sidebar h2 {
  margin-bottom: 10px;
}

.contacts-sidebar ul {
  list-style-type: none;
  padding: 0;
}

.contacts-sidebar li {
  cursor: pointer;
  padding: 5px;
  margin: 5px 0;
  background-color: white;
  border-radius: 5px;
  transition: background-color 0.2s;
}

.contacts-sidebar li:hover {
  background-color: #f5f5f5;
}

.contact-input {
  display: flex;
  align-items: center;
  padding: 10px;
  background-color: white;
  border-top: 1px solid #ccc;
}

.contact-input textarea {
  flex: 1;
  resize: none;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.contact-input button {
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 8px 12px;
  margin-left: 10px;
  cursor: pointer;
}

.back-button-send {
  position: fixed;
  bottom: 20px;
  left: 20px;
  width: 60px;
  height: 60px;
  background-color: #007bff;
  color: white;
  border-radius: 50%;
  text-align: center;
  line-height: 60px;
  cursor: pointer;
  font-size: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
