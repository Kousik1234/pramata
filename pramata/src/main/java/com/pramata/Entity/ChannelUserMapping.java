package com.pramata.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "channel_user")
public class ChannelUserMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer teamUserMappingId;
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
