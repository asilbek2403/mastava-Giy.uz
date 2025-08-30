package kun_Youtube.uz.controller;

import kun_Youtube.uz.dtov.subscription.SubscriptionCreateDto;
import kun_Youtube.uz.dtov.subscription.SubscriptionInfoDto;
import kun_Youtube.uz.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // 1. Create User Subscription (USER)
    @PostMapping("/create")
    public SubscriptionInfoDto createSubscription(@RequestParam Integer profileId,
                                                  @RequestBody SubscriptionCreateDto dto) {
        return subscriptionService.createSubscription(profileId, dto);
    }

    // 2. Change Subscription Status (USER)
    @PutMapping("/change-status")
    public SubscriptionInfoDto changeStatus(@RequestParam Integer profileId,
                                            @RequestParam String channelId,
                                            @RequestParam Boolean status) {
        return subscriptionService.changeStatus(profileId, channelId, status);
    }

    // 3. Change Subscription Notification Type (USER)
    @PutMapping("/change-notification")
    public SubscriptionInfoDto changeNotification(@RequestParam Integer profileId,
                                                  @RequestParam String channelId,
                                                  @RequestParam String notificationType) {
        return subscriptionService.changeNotification(profileId, channelId, notificationType);
    }

    // 4. Get User Subscription List (only Active) (USER)
    @GetMapping("/my")
    public List<SubscriptionInfoDto> getUserSubscriptions(@RequestParam Integer profileId) {
        return subscriptionService.getUserSubscriptions(profileId);
    }

    // 5. Get User Subscription List By UserId (only Active) (ADMIN)
    @GetMapping("/user/{userId}")
    public List<SubscriptionInfoDto> getUserSubscriptionsByUserId(@PathVariable Integer userId) {
        return subscriptionService.getUserSubscriptionsByUserId(userId);
    }
}
