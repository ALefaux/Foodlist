---
name: Home Pantry Design System
colors:
  surface: '#f9f9f8'
  surface-dim: '#d9dad9'
  surface-bright: '#f9f9f8'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f3f4f3'
  surface-container: '#edeeed'
  surface-container-high: '#e7e8e7'
  surface-container-highest: '#e1e3e2'
  on-surface: '#191c1c'
  on-surface-variant: '#42493e'
  inverse-surface: '#2e3131'
  inverse-on-surface: '#f0f1f0'
  outline: '#73796d'
  outline-variant: '#c2c9bb'
  surface-tint: '#3f6833'
  primary: '#37602c'
  on-primary: '#ffffff'
  primary-container: '#4f7942'
  on-primary-container: '#d3ffc1'
  inverse-primary: '#a4d393'
  secondary: '#8d4f11'
  on-secondary: '#ffffff'
  secondary-container: '#feac67'
  on-secondary-container: '#773e00'
  tertiary: '#884400'
  on-tertiary: '#ffffff'
  tertiary-container: '#ac5800'
  on-tertiary-container: '#fff0e8'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#c0f0ad'
  primary-fixed-dim: '#a4d393'
  on-primary-fixed: '#022100'
  on-primary-fixed-variant: '#28501e'
  secondary-fixed: '#ffdcc3'
  secondary-fixed-dim: '#ffb77d'
  on-secondary-fixed: '#2f1500'
  on-secondary-fixed-variant: '#6e3900'
  tertiary-fixed: '#ffdcc5'
  tertiary-fixed-dim: '#ffb783'
  on-tertiary-fixed: '#301400'
  on-tertiary-fixed-variant: '#713700'
  background: '#f9f9f8'
  on-background: '#191c1c'
  surface-variant: '#e1e3e2'
typography:
  headline-lg:
    fontFamily: Inter
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
    letterSpacing: -0.02em
  headline-lg-mobile:
    fontFamily: Inter
    fontSize: 26px
    fontWeight: '700'
    lineHeight: 32px
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Inter
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
  body-lg:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  label-md:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 16px
    letterSpacing: 0.05em
  label-sm:
    fontFamily: Inter
    fontSize: 11px
    fontWeight: '500'
    lineHeight: 14px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 4px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 32px
  gutter: 16px
  margin-mobile: 16px
  margin-desktop: 32px
  touch-target-min: 44px
---

## Brand & Style

The design system is built on the principle of **Modern Utility**. It serves as a reliable digital companion for the domestic environment, prioritizing clarity, efficiency, and food safety. The brand personality is organized, helpful, and fresh—evoking the feeling of a well-maintained kitchen.

The aesthetic blends **Minimalism** with **Corporate Modern** sensibilities to ensure the app feels like a high-end appliance: functional but sophisticated. The UI avoids unnecessary decorative elements, using whitespace and a refined color palette to reduce the cognitive load of managing complex inventory data. 

The target audience ranges from busy heads of households to culinary enthusiasts who value precision. The emotional response should be one of "calm control"—transforming the potential chaos of food waste into a streamlined, predictable system.

## Colors

The palette is rooted in organic, food-safe tones. 
- **Primary (Sage Green):** Used for main actions, active states, and branding. It represents freshness and growth.
- **Secondary (Warm Orange):** Specifically reserved for "Expiring Soon" warnings and moderate alerts.
- **Tertiary (Deep Amber):** Used for highlighting seasonal items or recipe suggestions.
- **Error (Deep Red):** Strictly for "Expired" items and critical system errors.
- **Neutrals:** A range of cool-toned greys (Light) and deep charcoals (Dark) provide the structural foundation.

The system supports a seamless transition between **Light** (default) and **Dark** modes. In Dark mode, surfaces use a deep charcoal rather than pure black to maintain readability and reduce eye strain in low-light pantry environments.

## Typography

This design system utilizes **Inter** for all typographic needs due to its exceptional legibility at small sizes and high-performance x-height, which is critical for reading labels and expiration dates on mobile devices.

Hierarchy is established through weight and color rather than excessive scale changes. Headlines use a tighter letter-spacing for a modern, compact look, while labels utilize increased tracking for readability when used in high-density inventory lists. For mobile displays, the `headline-lg` scales down to ensure long food names do not wrap awkwardly.

## Layout & Spacing

The layout employs a **Fluid Grid** system that adapts across mobile (4 columns), tablet (8 columns), and desktop (12 columns). 

- **Rhythm:** A strict 4px base unit governs all spatial relationships. 
- **Touch Targets:** A minimum target of 44x44px is enforced for all interactive elements to ensure accessibility while cooking or handling groceries.
- **Margins:** 16px side margins on mobile provide breathing room, expanding to 32px or more on larger screens to prevent line lengths from becoming unreadable.
- **Containers:** Content is grouped in logical clusters using cards with 16px internal padding.

## Elevation & Depth

To achieve a "Modern Utility" look, the system uses **Tonal Layers** supplemented by **Ambient Shadows**. 

- **Level 0 (Background):** The base canvas (Neutral 50 in light mode, Neutral 900 in dark mode).
- **Level 1 (Cards/Surfaces):** Slightly elevated using a very soft, diffused shadow (10% opacity, 4px blur) to separate inventory items from the background.
- **Level 2 (Modals/Popovers):** Higher elevation with a 15% opacity shadow and 12px blur, signaling temporary interaction.

In Dark mode, elevation is communicated primarily through lighter surface tones (Grey-on-Grey) rather than shadows to maintain a clean, flat appearance.

## Shapes

The design system utilizes a **Rounded** shape language to feel approachable and modern. 
- Standard UI components (Buttons, Inputs) use a `0.5rem` (8px) radius.
- Inventory Cards and large containers use `1rem` (16px) radius to create a soft, friendly "container" for items.
- Status Chips (Expired/Fresh) utilize a full pill-shape (999px) to distinguish them as non-interactive data points.

## Components

- **Buttons:** Primary buttons are solid Sage Green with white text. Secondary buttons use a Sage Green outline. All buttons must have a height of 48px to exceed the 44px touch-target minimum.
- **Inventory Cards:** Feature a high-contrast title, a secondary line for quantity, and a distinct status indicator. 
- **Status Indicators:** 
    - *Expired:* Red background, white text, bold label.
    - *Expiring Soon:* Orange background, dark text.
    - *Fresh:* Subtle light green background, primary green text.
- **Input Fields:** Minimalist containers with a 1px border. In focus states, the border thickens to 2px in Primary Sage. Label text remains visible above the field (Material 3 style) to maintain context.
- **Navigation:** A bottom navigation bar for mobile with 24px line icons and 11px labels. For Desktop, this transitions to a sidebar.
- **Chips/Filters:** Used at the top of lists for quick storage location switching (Pantry, Fridge, Freezer). These use a light neutral fill when inactive and Sage Green fill when active.