<script setup lang="ts">
const runtimeConfig = useRuntimeConfig();
// console.log(runtimeConfig.public.apiBaseUrl);

interface Ticket {
  id: number;
  message: string;
}

const error = ref("");

const token = ref("");

const username = ref("");
const password = ref("");

const headers = computed(() => ({
  Authorization: `Bearer ${token.value}`,
}));

const tickets = ref<Ticket[]>([]);

async function getTickets() {
  try {
    const res = await $fetch(`${runtimeConfig.public.apiBaseUrl}/api/tickets`, {
      headers: headers.value,
    });
    // console.log({ res });
    if (Array.isArray(res)) {
      tickets.value = res;
    } else {
      error.value = "La récupération des données a échouée.";
    }
  } catch (err) {
    console.error(err);
    error.value = "La récupération des données a échouée.";
  }
}

async function handleSignIn() {
  token.value = "";
  error.value = "";
  //   console.log({ username: username.value, password: password.value });
  try {
    const res = await $fetch(
      `${runtimeConfig.public.apiBaseUrl}/api/auth/signin`,
      {
        method: "POST",
        body: {
          username: username.value,
          password: password.value,
        },
      },
    );
    // console.log({ res });
    if (res) {
      token.value = String(res);
      await getTickets();
    } else {
      error.value = "La connexion a échouée.";
    }
  } catch (err) {
    console.error(err);
    error.value = "La connexion a échouée.";
  }
}

const message = ref("");
const editing = ref(0);
const editingMessage = ref("");

async function handleSubmit() {
  //   console.log(message.value);
  const res = await $fetch(`${runtimeConfig.public.apiBaseUrl}/api/tickets`, {
    method: "POST",
    body: {
      message: message.value,
    },
    headers: headers.value,
  });
  //   console.log({ res });
  message.value = "";
  await getTickets();
}

async function handleUpdate() {
  //   console.log(editingMessage.value);
  const res = await $fetch(
    `${runtimeConfig.public.apiBaseUrl}/api/tickets/${editing.value}`,
    {
      method: "PUT",
      body: {
        message: editingMessage.value,
      },
      headers: headers.value,
    },
  );
  //   console.log({ res });
  editing.value = 0;
  editingMessage.value = "";
  await getTickets();
}

async function handleDelete(id: number) {
  //   console.log(`deleting #${id}`);
  const res = await $fetch(
    `${runtimeConfig.public.apiBaseUrl}/api/tickets/${id}`,
    {
      method: "DELETE",
      headers: headers.value,
    },
  );
  //   console.log({ res });
  await getTickets();
}
</script>

<template>
  <div>
    <NuxtRouteAnnouncer />

    <div v-if="error" style="color: red">{{ error }}</div>

    <h1>Gestion de mes Tickets</h1>

    <div style="margin-bottom: 1em">
      <div v-if="token">
        Connecté en tant que
        <span style="font-weight: bold">{{ username }}</span>
      </div>
      <div v-else>Non connecté. Connectez-vous pour gérer vos tickets.</div>
    </div>

    <div v-if="token">
      <form
        class="flex items-center"
        @submit.prevent="handleSubmit"
        style="margin-bottom: 1em"
      >
        <input type="text" placeholder="Entrer le message" v-model="message" />
        <button type="submit">Créer un ticket</button>
      </form>

      <div v-if="tickets.length == 0">No data</div>

      <div v-else>
        <!-- {{ data }} -->
        <template v-for="item in tickets">
          <div class="ticket-card flex items-center">
            <span style="font-weight: bold">#{{ item.id }}</span>

            <input
              v-if="editing == item.id"
              type="text"
              v-model="editingMessage"
            />
            <span v-else>{{ item.message }}</span>

            <svg
              v-if="editing == item.id"
              @click="handleUpdate"
              xmlns="http://www.w3.org/2000/svg"
              width="20"
              height="20"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="lucide lucide-check-icon lucide-check"
            >
              <path d="M20 6 9 17l-5-5" />
            </svg>

            <svg
              v-else
              @click="
                editing = item.id;
                editingMessage = item.message;
              "
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="lucide lucide-pencil-icon lucide-pencil"
            >
              <path
                d="M21.174 6.812a1 1 0 0 0-3.986-3.987L3.842 16.174a2 2 0 0 0-.5.83l-1.321 4.352a.5.5 0 0 0 .623.622l4.353-1.32a2 2 0 0 0 .83-.497z"
              />
              <path d="m15 5 4 4" />
            </svg>

            <svg
              @click="() => handleDelete(item.id)"
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              viewBox="0 0 24 24"
              fill="none"
              stroke="red"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
              class="lucide lucide-trash-icon lucide-trash"
            >
              <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6" />
              <path d="M3 6h18" />
              <path d="M8 6V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" />
            </svg>
          </div>
        </template>
      </div>
    </div>

    <div v-else>
      <form @submit.prevent="handleSignIn" class="flex flex-column">
        <input type="text" placeholder="Nom d'utilisateur" v-model="username" />
        <input type="password" placeholder="Mot de passe" v-model="password" />
        <button type="submit">Se connecter</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.ticket-card {
  border: 1px solid gray;
  padding: 1em;
  width: fit-content;
  margin-bottom: 1em;
}

.flex {
  display: flex;
  gap: 1em;
}

.flex-column {
  flex-direction: column;
}

.items-center {
  align-items: center;
}

input {
  padding: 8px;
}

button {
  padding: 8px;
  cursor: pointer;
}

svg {
  cursor: pointer;
}
</style>
