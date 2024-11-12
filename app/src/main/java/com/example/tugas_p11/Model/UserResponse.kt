package com.example.tugas_p11.Model

data class UserResponse(val page: Int,
                        val per_page: Int,
                        val total: Int,
                        val total_pages: Int,
                        val data: List<User>)
