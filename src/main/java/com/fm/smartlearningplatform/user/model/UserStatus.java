package com.fm.smartlearningplatform.user.model;

public enum UserStatus {

    // --- Onboarding ---
    PENDING_VERIFICATION,   // registered but email not verified yet
    VERIFIED,               // email verified, profile incomplete
    ACTIVE,                 // fully onboarded and in good standing

    // --- Restricted ---
    SUSPENDED,              // temporarily restricted, can be reversed
    BLOCKED,                // blocked by admin, cannot log in
    BANNED,                 // permanent ban, stronger than blocked

    // --- Inactive ---
    INACTIVE,               // no activity for a long time (auto-set)
    DEACTIVATED,            // user chose to deactivate their own account
    DELETED,                // soft-deleted, data retained for audit

    // --- Special ---
    UNDER_REVIEW,           // flagged, awaiting admin decision
    LOCKED,                 // too many failed login attempts (auto-set)
    PASSWORD_RESET_REQUIRED;// must reset password before proceeding

    public boolean isEnabled() {
        return this == ACTIVE || this == VERIFIED;
    }

    public boolean isAccountNonLocked() {
        return this != BLOCKED
                && this != BANNED
                && this != LOCKED
                && this != SUSPENDED;
    }

    public boolean isLoginAllowed() {
        return isEnabled() && isAccountNonLocked();
    }

    public boolean isSoftDeleted() {
        return this == DELETED || this == DEACTIVATED;
    }
}

//      Register
//         ↓
//      PENDING_VERIFICATION
//         ↓ (email verified)
//      VERIFIED
//         ↓ (profile complete)
//      ACTIVE
//         ↓
//        ┌─────────────────────────────────┐
//        │                                 │
//      SUSPENDED        LOCKED        UNDER_REVIEW
//       │  (reversed)    │ (reset)        │
//       └──────→ ACTIVE ←┘           BLOCKED / BANNED
//                                         │
//                                  DEACTIVATED / DELETED